package com.turing.backendapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeRequest {
  private String stripeToken;
  private Integer orderId;
  private String description;
  private int amount;

  private String currency;

}
