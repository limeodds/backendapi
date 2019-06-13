package com.turing.backendapi.controller;

import com.turing.backendapi.authentication.JwtTokenProvider;
import com.turing.backendapi.controller.dto.CustomerDto;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.domain.Customer;
import com.turing.backendapi.service.CustomerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static com.turing.backendapi.controller.converter.CustomerDtoConverter.toDto;
import static com.turing.backendapi.controller.exception.ErrorCodes.*;

/**
 * Documentation: https://medium.com/@hantsy/protect-rest-apis-with-spring-security-and-jwt-5fbc90305cc5
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Customers", tags = {"customers"})
public class CustomerController implements Authentication {

  private final CustomerService customerService;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;

  @Autowired
  public CustomerController(CustomerService customerService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
    this.customerService = customerService;
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @PutMapping("/customer")
  @ApiOperation(value = "Update a customer")
  @ApiImplicitParams({@ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header")})
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "A Customer Object"),
  @ApiResponse(code = 400, message = "Return a error object")})
  public CustomerDto updateCustomer(
  @ApiParam(value = "Customer name.", required = true) @RequestParam String name,
  @ApiParam(value = "Customer email.", required = true) @RequestParam String email,
  @ApiParam(value = "Customer password.") @RequestParam String password,
  @ApiParam(value = "Customer day phone.") @RequestParam String day_phone,
  @ApiParam(value = "Customer eve phone.") @RequestParam String eve_phone,
  @ApiParam(value = "Customer mob phone.") @RequestParam String mob_phone) {
    if (StringUtils.isEmpty(name)) {
      throw new BadRequestException(GEN_01.getCode(), GEN_01.getDescription(), "name");
    }
    if (StringUtils.isEmpty(email)) {
      throw new BadRequestException(GEN_01.getCode(), GEN_01.getDescription(), "email");
    }

    Customer byId = customerService.getById(getPrincipal().getId());

    if (byId == null) {
      throw new BadRequestException(CST_01.getCode(), CST_01.getDescription(), "customer_id");
    }


    byId.setName(name);
    byId.setEmail(email);
    byId.setPassword(password);
    byId.setDay_phone(day_phone);
    byId.setEve_phone(eve_phone);
    byId.setMob_phone(mob_phone);

    byId = customerService.save(byId);

    return toDto(byId);
  }

  @PostMapping("/customers")
  @ApiOperation(value = "Register a Customer")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return a Object of Customer with auth credentials"),
  @ApiResponse(code = 400, message = "Return a error object")})
  public Map<String, Object> register(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
    if (StringUtils.isEmpty(name) || StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
      throw new BadRequestException(USR_02.getCode(), USR_02.getDescription(), "name, email, password");
    }

    if (customerService.getByEmail(email) != null) {
      throw new BadRequestException(USR_04.getCode(), USR_04.getDescription(), "email");
    }

    Customer newCustomer = Customer.builder()
                                   .name(name)
                                   .email(email)
                                   .password(password)
                                   .build();

    Customer savedCustomer = customerService.save(newCustomer);

    String token = authenticate(email, password);

    return Map.of("customer", toDto(savedCustomer), "accessToken", token, "expires_in", "24h");
  }

  @PostMapping(value = "/customers/login")
  @ApiOperation(value = "Sign in in the Shopping.")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return a Object of Customer with auth credentials"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public Map<String, Object> login(@RequestParam String email, @RequestParam String password) {
    if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
      throw new BadRequestException(USR_02.getCode(), USR_02.getDescription(), "email, password");
    }

    Customer customer = customerService.getByEmail(email);
    if (customer == null) {
      throw new BadRequestException(USR_01.getCode(), USR_01.getDescription(), "email, password");
    }

    String token = authenticate(email, password);

    return Map.of("customer", toDto(customer), "accessToken", token, "expires_in", "24h");
  }

  private String authenticate(String email, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    return jwtTokenProvider.createToken(email, Collections.emptyList());
  }
}
