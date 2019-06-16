package com.turing.backendapi.controller;


import com.stripe.model.Charge;
import com.turing.backendapi.controller.exception.ErrorResponse;
import com.turing.backendapi.controller.exception.UnauthorizedResponse;
import com.turing.backendapi.domain.ChargeRequest;
import com.turing.backendapi.service.StripeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.turing.backendapi.controller.exception.ErrorCodes.GEN_01;

@RestController
@RequestMapping(value = "/stripe", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Stripe Ingregation and Webhooks", tags = {"stripe"})
public class StripeController implements Authentication, Validation {

  private final StripeService stripeService;

  @Autowired
  public StripeController(StripeService stripeService) {
    this.stripeService = stripeService;
  }

  @PostMapping(path = "/charge", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ApiOperation(value = "You can send a cart information and payment token (https://stripe.com/docs/api/tokens).")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return the Order ID", response = Charge.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class),
                @ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "stripeToken", value = "The API token, you can use this example to get it: https://stripe.com/docs/stripe-js/elements/quickstart", required = true,
                                       dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "order_id", value = "The order ID recorded before (Check the Order Documentation)", required = true, dataType = "int", paramType = "form"),
                     @ApiImplicitParam(name = "description", value = "Description to order.", required = true, dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "amount", value = "Only numbers like: 999", required = true, dataType = "int", paramType = "form"),
                     @ApiImplicitParam(name = "currency", value = "Check here the options: https://stripe.com/docs/currencies, the default USD", dataType = "string", paramType =
                     "form")})
  public Charge createOrder(@RequestParam String stripeToken,
                            @RequestParam Integer order_id,
                            @RequestParam String description,
                            @RequestParam Integer amount,
                            @RequestParam(required = false, defaultValue = "USD") String currency) {
    checkNotEmpty(stripeToken, GEN_01, "stripeToken");
    checkNotEmpty(order_id, GEN_01, "order_id");
    checkNotEmpty(description, GEN_01, "description");
    checkNotEmpty(amount, GEN_01, "amount");


    ChargeRequest cr = ChargeRequest.builder()
                                    .stripeToken(stripeToken)
                                    .orderId(order_id)
                                    .description(description)
                                    .amount(amount)
                                    .currency(currency)
                                    .build();

    return stripeService.charge(cr);
  }

  @PostMapping(path = "/webhooks")
  @ApiOperation(value = "Endpoint that provide a synchronization")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return the Order ID", response = Map.class)})
  public Map<String, Boolean> webhooks() {
    return Map.of("received", true);
  }

}
