package com.turing.backendapi.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttributeDto {
  private String attribute_name;
  private int attribute_value_id;
  private String attribute_value;
}
