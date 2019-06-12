package com.turing.backendapi.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInDepartmentDto {
  private int product_id;
  private String name;
  private String description;
  private String price;
  private String discounted_price;
  private String thumbnail;
  private int display;
}
