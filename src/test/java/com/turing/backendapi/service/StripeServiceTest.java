package com.turing.backendapi.service;

import com.stripe.model.Charge;
import com.turing.backendapi.domain.ChargeRequest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * https://stripe.com/docs/api/metadata
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StripeServiceTest {

  @Autowired
  private StripeService stripeService;

  @Test
  public void charge() {
    ChargeRequest cr = ChargeRequest.builder()
                                    .stripeToken("tok_mastercard")
                                    .orderId(1)
                                    .description("Description to order")
                                    .amount(1100)
                                    .currency("USD")
                                    .build();

    Charge charge = stripeService.charge(cr);

    Assertions.assertThat(charge).isNotNull();
  }
}