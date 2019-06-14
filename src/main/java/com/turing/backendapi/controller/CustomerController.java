package com.turing.backendapi.controller;

import com.turing.backendapi.authentication.JwtTokenProvider;
import com.turing.backendapi.controller.dto.CustomerDto;
import com.turing.backendapi.controller.dto.LoginResponseDto;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.controller.exception.ErrorResponse;
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
public class CustomerController implements Authentication, Validation {

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
    checkNotEmpty(name, GEN_01, "name");
    checkNotEmpty(email, GEN_01, "email");

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

  @GetMapping("/customer")
  @ApiOperation(value = "Get a customer by ID. The customer is getting by Token.")
  @ApiImplicitParams({@ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header")})
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "A Customer Object"),
  @ApiResponse(code = 400, message = "Return a error object")})
  public CustomerDto getCustomer() {
    Customer byId = customerService.getById(getPrincipal().getId());

    if (byId == null) {
      throw new BadRequestException(CST_01.getCode(), CST_01.getDescription(), "token");
    }
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

  @PostMapping(path = "/customers/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ApiOperation(value = "Sign in in the Shopping.", notes = "Customer Login.")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a Object of Customer with auth credentials", response = LoginResponseDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "email", value = "Email of User.", required = true, dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "password", value = "Password of User.", required = true, dataType = "string", paramType = "form")
                     })
  public LoginResponseDto login(@RequestParam String email,
                                   @RequestParam String password) {
    if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
      throw new BadRequestException(USR_02.getCode(), USR_02.getDescription(), "email, password");
    }

    Customer customer = customerService.getByEmail(email);
    if (customer == null) {
      throw new BadRequestException(USR_05.getCode(), USR_05.getDescription(), "email");
    }

    String token = authenticate(email, password);

    return LoginResponseDto.builder()
                           .customer(new LoginResponseDto.Customer(toDto(customer)))
                           .accessToken(token)
                           .expires_in("24h")
                           .build();
  }

  private String authenticate(String email, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    return jwtTokenProvider.createToken(email, Collections.emptyList());
  }

  @PutMapping("/customers/address")
  @ApiOperation(value = "Update the address from customer")
  @ApiImplicitParams({@ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header")})
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "A Customer Object"),
  @ApiResponse(code = 400, message = "Return a error object")})
  public CustomerDto updateAddress(
  @ApiParam(value = "Address 1", required = true) @RequestParam String address_1,
  @ApiParam(value = "Address 2") @RequestParam String address_2,
  @ApiParam(value = "City", required = true) @RequestParam String city,
  @ApiParam(value = "Region", required = true) @RequestParam String region,
  @ApiParam(value = "Postal Code", required = true) @RequestParam String postal_code,
  @ApiParam(value = "Country", required = true) @RequestParam String country,
  @ApiParam(value = "Shipping Region ID", required = true) @RequestParam Integer shipping_region_id) {

    checkNotEmpty(address_1, GEN_01, "address_1");
    checkNotEmpty(city, GEN_01, "city");
    checkNotEmpty(region, GEN_01, "region");
    checkNotEmpty(postal_code, GEN_01, "postal_code");
    checkNotEmpty(country, GEN_01, "country");
    checkNotEmpty(shipping_region_id, GEN_01, "shipping_region_id");

    Customer byId = customerService.getById(getPrincipal().getId());

    if (byId == null) {
      throw new BadRequestException(CST_01.getCode(), CST_01.getDescription(), "customer_id");
    }

    byId.setAddress_1(address_1);
    byId.setAddress_1(address_2);
    byId.setCity(city);
    byId.setRegion(region);
    byId.setPostal_code(postal_code);
    byId.setCountry(country);
    byId.setShipping_region_id(shipping_region_id);

    byId = customerService.save(byId);

    return toDto(byId);
  }

  @PutMapping("/customers/creditCard")
  @ApiOperation(value = "Update the credit card from customer")
  @ApiImplicitParams({@ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header")})
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "A Customer Object"),
  @ApiResponse(code = 400, message = "Return a error object")})
  public CustomerDto updateCreditCard(
  @ApiParam(value = "Address 1", required = true) @RequestParam String credit_card) {

    checkNotEmpty(credit_card, GEN_01, "credit_card ");

    Customer byId = customerService.getById(getPrincipal().getId());

    if (byId == null) {
      throw new BadRequestException(CST_01.getCode(), CST_01.getDescription(), "customer_id");
    }

    byId.setCredit_card(credit_card);

    byId = customerService.save(byId);

    return toDto(byId);
  }

  //TODO facebook login
}
