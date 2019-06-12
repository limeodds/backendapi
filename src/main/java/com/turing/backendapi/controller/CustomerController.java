package com.turing.backendapi.controller;

import static com.turing.backendapi.controller.exception.ErrorCodes.USR_01;
import static com.turing.backendapi.controller.exception.ErrorCodes.USR_02;
import static com.turing.backendapi.controller.exception.ErrorCodes.USR_04;

import com.turing.backendapi.authentication.JwtTokenProvider;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.controller.exception.ErrorCodes;
import com.turing.backendapi.domain.Customer;
import com.turing.backendapi.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Documentation:
 * https://medium.com/@hantsy/protect-rest-apis-with-spring-security-and-jwt-5fbc90305cc5
 */
@RestController
@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Customers", tags = { "customers" })
public class CustomerController {

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public CustomerController(CustomerService customerService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    @ApiOperation(value = "Register a Customer")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return a Object of Customer with auth credentials"),
        @ApiResponse(code = 400, message = "Return a error object")
    }
    )
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

        return Map.of("customer", savedCustomer, "accessToken", token, "expires_in", "24h");
    }

    @PostMapping(value = "/login")
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

        return Map.of("customer", customer, "accessToken", token, "expires_in", "24h");
    }

    private String authenticate(String email, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return jwtTokenProvider.createToken(email, Collections.emptyList());
    }
}
