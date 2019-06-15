package com.turing.backendapi.controller.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttributeDto {
  @ApiModelProperty(example = "Color", position = 1)
  private String attribute_name;

  @ApiModelProperty(example = "4", position = 2)
  private int attribute_value_id;

  @ApiModelProperty(example = "Orange", position = 3)
  private String attribute_value;
}
