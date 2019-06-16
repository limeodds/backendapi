package com.turing.backendapi.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsDto {

  @ApiModelProperty(example = "1", position = 1)
  private int order_id;

  @ApiModelProperty(example = "1", position = 2)
  private int product_id;

  @ApiModelProperty(example = "LG, Red", position = 3)
  private String attributes;

  @ApiModelProperty(example = "Arc d'Triomphe", position = 4)
  private String product_name;

  @ApiModelProperty(example = "1", position = 5)
  private int quantity;

  @ApiModelProperty(example = "14.99", position = 6)
  private String unit_cost;

  @ApiModelProperty(example = "14.99", position = 7)
  private String subtotal;
}
