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
public class CartProduct {
  private int item_id;
  private String name;
  private String attributes;
  private int product_id;
  private BigDecimal price;
  private int quantity;
  private String image;
  private BigDecimal subtotal;
}
