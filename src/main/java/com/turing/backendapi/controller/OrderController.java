package com.turing.backendapi.controller;


import static com.turing.backendapi.controller.exception.ErrorCodes.GEN_01;

import com.turing.backendapi.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Orders", tags = {"orders"})
public class OrderController implements Authentication, Validation {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ApiOperation(value = "Create a Order")
    @ApiImplicitParams({@ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header")})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return the Order ID"),
        @ApiResponse(code = 400, message = "Return a error object")})
    public Map<String, Integer> createOrder(
        @ApiParam(value = "Cart ID", required = true) @RequestParam String cart_id,
        @ApiParam(value = "Shipping ID") @RequestParam(required = false) Integer shipping_id,
        @ApiParam(value = "Tax ID") @RequestParam(required = false) Integer tax_id) {
        checkNotEmpty(cart_id, GEN_01, "cart_id");
        checkNotEmpty(shipping_id, GEN_01, "shipping_id");
        checkNotEmpty(tax_id, GEN_01, "tax_id");

        int orderId = orderService.createOrder(cart_id, getPrincipal().getId(), shipping_id, tax_id);

        return Map.of("orderId", orderId);
    }
}
