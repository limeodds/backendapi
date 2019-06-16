package com.turing.backendapi.controller;


import com.turing.backendapi.controller.converter.OrderDtoConverter;
import com.turing.backendapi.controller.dto.OrderDetailsDto;
import com.turing.backendapi.controller.dto.OrderIdDto;
import com.turing.backendapi.controller.dto.OrderShortDetailsDto;
import com.turing.backendapi.controller.exception.ErrorResponse;
import com.turing.backendapi.controller.exception.NotFoundException;
import com.turing.backendapi.controller.exception.NotFoundResponse;
import com.turing.backendapi.controller.exception.UnauthorizedResponse;
import com.turing.backendapi.domain.OrderDetails;
import com.turing.backendapi.domain.OrderShortDetails;
import com.turing.backendapi.service.OrderService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.turing.backendapi.controller.exception.ErrorCodes.GEN_01;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Orders", tags = {"orders"})
public class OrderController implements Authentication, Validation {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ApiOperation(value = "Create a Order")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return the Order ID", response = OrderIdDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class),
                @ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header"),
                     @ApiImplicitParam(name = "cart_id", value = "Cart ID", required = true, dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "shipping_id", value = "Shipping ID", required = true, dataType = "int", paramType = "form"),
                     @ApiImplicitParam(name = "tax_id", value = "Tax ID", required = true, dataType = "int", paramType = "form")})
  public OrderIdDto createOrder(@RequestParam String cart_id,
                                @RequestParam Integer shipping_id,
                                @RequestParam Integer tax_id) {
    checkNotEmpty(cart_id, GEN_01, "cart_id");
    checkNotEmpty(shipping_id, GEN_01, "shipping_id");
    checkNotEmpty(tax_id, GEN_01, "tax_id");

    int orderId = orderService.createOrder(cart_id, getPrincipal().getId(), shipping_id, tax_id);

    return new OrderIdDto(orderId);
  }

  @GetMapping("/{order_id}")
  @ApiOperation(value = "Get Info about Order")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a Product Object", response = OrderDetailsDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class),
                @ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedResponse.class),
                @ApiResponse(code = 404, message = "Not Found", response = NotFoundResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header"),
                     @ApiImplicitParam(name = "order_id", value = "Order ID", required = true, dataType = "int", paramType = "path")})
  public OrderDetailsDto getById(@PathVariable Integer order_id) {
    checkNotEmpty(order_id, GEN_01, "order_id");

    OrderDetails orderDetails = orderService.getOrderDetails(order_id);

    if (orderDetails == null) {
      throw new NotFoundException("Order not found");
    }

    return OrderDtoConverter.toDto(orderDetails);
  }

  @GetMapping("/inCustomer")
  @ApiOperation(value = "Get orders by Customer")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a array of Orders", response = OrderShortDetailsDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class),
                @ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header")})
  public OrderShortDetailsDto[] getOrdersForCustomer() {
    return orderService.getOrdersByCustomer(getPrincipal().getId()).stream()
                       .map(OrderDtoConverter::toDto).toArray(OrderShortDetailsDto[]::new);
  }

  @GetMapping("shortDetail/{order_id}")
  @ApiOperation(value = "Get Info about Order")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a Object Order", response = OrderShortDetailsDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class),
                @ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header"),
                     @ApiImplicitParam(name = "order_id", value = "Order ID", required = true, dataType = "int", paramType = "path")})
  public OrderShortDetailsDto getOrderShortDetails(@PathVariable Integer order_id) {
    checkNotEmpty(order_id, GEN_01, "order_id");

    OrderShortDetails osd = orderService.getOrderShortDetails(order_id);

    if (osd == null) {
      throw new NotFoundException("Order not found");
    }

    return OrderDtoConverter.toDto(osd);
  }
}
