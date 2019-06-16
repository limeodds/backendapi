package com.turing.backendapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetails {
  private int order_id;
  private int product_id;
  private String attributes;
  private String product_name;
  private int quantity;
  private BigDecimal unit_cost;
  private BigDecimal subtotal;
}
