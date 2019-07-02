package com.turing.backendapi.controller;

import com.turing.backendapi.authentication.JwtTokenProvider;
import com.turing.backendapi.controller.dto.CustomerDto;
import com.turing.backendapi.controller.dto.LoginResponseDto;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.controller.exception.ErrorResponse;
import com.turing.backendapi.controller.exception.UnauthorizedResponse;
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

import static com.turing.backendapi.authentication.JwtTokenProvider.AUTHORISATION_HEADER_NAME;
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

  @PutMapping(path = "/customer", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ApiOperation(value = "Update a customer")
  @ApiResponses({
                @ApiResponse(code = 200, message = "A Customer Object", response = CustomerDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class),
                @ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = AUTHORISATION_HEADER_NAME, value = "JWT Token", dataType = "string", paramType = "header"),
                     @ApiImplicitParam(name = "name", value = "Customer name.", required = true, dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "email", value = "Customer email.", required = true, dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "password", value = "Customer password.", dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "day_phone", value = "Customer day phone.", dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "eve_phone", value = "Customer eve phone.", dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "mob_phone", value = "Customer mob phone.", dataType = "string", paramType = "form")})
  public CustomerDto updateCustomer(@RequestParam String name,
                                    @RequestParam String email,
                                    @RequestParam(required = false) String password,
                                    @RequestParam(required = false) String day_phone,
                                    @RequestParam(required = false) String eve_phone,
                                    @RequestParam(required = false) String mob_phone) {
    checkNotEmpty(name, GEN_01, "name");
    checkNotEmpty(email, GEN_01, "email");

    Customer byId = customerService.getById(getPrincipal().getId());

    if (!email.equalsIgnoreCase(byId.getEmail())) {
      //check if there is another customer with the same email
      if (customerService.getByEmail(email) != null) {
        throw new BadRequestException(USR_04.getCode(), USR_04.getDescription(), "email");
      }
    }

    byId.setName(name);
    byId.setEmail(email);
    if (!StringUtils.isEmpty(password)) {
      byId.setPasswordHash(customerService.hashPassword(password, byId.getSalt()));
    }
    byId.setDay_phone(day_phone);
    byId.setEve_phone(eve_phone);
    byId.setMob_phone(mob_phone);

    byId = customerService.save(byId);

    return toDto(byId);
  }

  @GetMapping("/customer")
  @ApiOperation(value = "Get a customer by ID. The customer is getting by Token.")
  @ApiResponses({
                @ApiResponse(code = 200, message = "A Customer Object", response = CustomerDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class),
                @ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedResponse.class)})
  @ApiImplicitParams({@ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header")})
  public CustomerDto getCustomer() {
    Customer byId = customerService.getById(getPrincipal().getId());

    return toDto(byId);
  }

  @PostMapping(path = "/customers", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ApiOperation(value = "Register a Customer", notes = "Record a customer.")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a Object of Customer with auth credentials", response = LoginResponseDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "name", value = "Name of User.", required = true, dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "email", value = "Email of User.", required = true, dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "password", value = "Password of User.", required = true, dataType = "string", paramType = "form")})
  public LoginResponseDto register(@RequestParam String name,
                                   @RequestParam String email,
                                   @RequestParam String password) {
    if (StringUtils.isEmpty(name) || StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
      throw new BadRequestException(USR_02.getCode(), USR_02.getDescription(), "name, email, password");
    }

    if (customerService.getByEmail(email) != null) {
      throw new BadRequestException(USR_04.getCode(), USR_04.getDescription(), "email");
    }

    String salt = customerService.getNextSalt();
    String passwordHash = customerService.hashPassword(password, salt);

    Customer newCustomer = Customer.builder()
                                   .name(name)
                                   .email(email)
                                   .passwordHash(passwordHash)
                                   .salt(salt)
                                   .build();

    Customer savedCustomer = customerService.save(newCustomer);

    return LoginResponseDto.builder()
                           .customer(new LoginResponseDto.Customer(toDto(savedCustomer)))
                           .accessToken(authenticate(email, passwordHash))
                           .expires_in("24h")
                           .build();
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

    if(!customer.getPasswordHash().equals(customerService.hashPassword(password, customer.getSalt()))){
      throw new BadRequestException(USR_01.getCode(), USR_01.getDescription(), "email, password");
    }

    return LoginResponseDto.builder()
                           .customer(new LoginResponseDto.Customer(toDto(customer)))
                           .accessToken(authenticate(email, customer.getPasswordHash()))
                           .expires_in("24h")
                           .build();
  }

  private String authenticate(String email, String passwordHash) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, passwordHash));
    return jwtTokenProvider.createToken(email, Collections.emptyList());
  }

  @PutMapping(path = "/customers/address", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ApiOperation(value = "Update the address from customer")
  @ApiResponses({
                @ApiResponse(code = 200, message = "A Customer Object", response = LoginResponseDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class),
                @ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header"),
                     @ApiImplicitParam(name = "address_1", value = "Address 1", required = true, dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "address_2", value = "Address 2", dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "city", value = "City", required = true, dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "postal_code", value = "Postal Code", required = true, dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "shipping_region_id", value = "Shipping Region ID", required = true, dataType = "string", paramType = "form")})
  public CustomerDto updateAddress(@RequestParam String address_1,
                                   @RequestParam(required = false) String address_2,
                                   @RequestParam String city,
                                   @RequestParam String region,
                                   @RequestParam String postal_code,
                                   @RequestParam String country,
                                   @RequestParam Integer shipping_region_id) {
    checkNotEmpty(address_1, GEN_01, "address_1");
    checkNotEmpty(city, GEN_01, "city");
    checkNotEmpty(region, GEN_01, "region");
    checkNotEmpty(postal_code, GEN_01, "postal_code");
    checkNotEmpty(country, GEN_01, "country");
    checkNotEmpty(shipping_region_id, GEN_01, "shipping_region_id");

    Customer byId = customerService.getById(getPrincipal().getId());

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

  @PutMapping(path = "/customers/creditCard", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ApiOperation(value = "Update the credit card from customer")
  @ApiResponses({
                @ApiResponse(code = 200, message = "A Customer Object", response = LoginResponseDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class),
                @ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header"),
                     @ApiImplicitParam(name = "credit_card", value = "Credit Card.", required = true, dataType = "string", paramType = "form")})
  public CustomerDto updateCreditCard(@RequestParam String credit_card) {

    checkNotEmpty(credit_card, GEN_01, "credit_card ");

    Customer byId = customerService.getById(getPrincipal().getId());

    byId.setCredit_card(credit_card);

    byId = customerService.save(byId);

    return toDto(byId);
  }

  //TODO facebook login
}
