package com.turing.backendapi.service;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.domain.ChargeRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@Slf4j
public class StripeService {
  @Getter
  @Value("${stripe.secret.key}")
  private String secretKey;

  @PostConstruct
  public void init() {
    Stripe.apiKey = secretKey;
  }

  public Charge charge(ChargeRequest chargeRequest) {
    log.debug("charge(chargeRequest: {})", chargeRequest);

    Map<String, Object> chargeParams = new HashMap<>();
    chargeParams.put("amount", chargeRequest.getAmount());
    chargeParams.put("currency", chargeRequest.getCurrency());
    chargeParams.put("description", chargeRequest.getDescription());
    chargeParams.put("source", chargeRequest.getStripeToken());

    chargeParams.put("metadata", Map.of("order_id", String.valueOf(chargeRequest.getOrderId())));

    try {
      return Charge.create(chargeParams);
    } catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
      throw new BadRequestException("STR-01", "Error calling stripe.charge: " + e.getMessage(), "");
    }
  }
}
