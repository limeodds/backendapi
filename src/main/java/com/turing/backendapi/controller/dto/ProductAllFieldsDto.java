package com.turing.backendapi.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAllFieldsDto {
  private int product_id;
  private String name;
  private String description;
  private String price;
  private String discounted_price;
  private String image;
  private String image_2;
  private String thumbnail;
  private int display;
}
