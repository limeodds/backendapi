package com.turing.backendapi.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailsDto {
  private int product_id;
  private String name;
  private String description;
  private String price;
  private String discounted_price;
  private String image;
  private String image_2;
}
