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
public class CartProductDto {
  @ApiModelProperty(example = "2", position = 1)
  private int item_id;

  @ApiModelProperty(example = "Arc d'Triomphe", position = 2)
  private String name;

  @ApiModelProperty(example = "LG, red", position = 3)
  private String attributes;

  @ApiModelProperty(example = "2", position = 4)
  private int product_id;

  @ApiModelProperty(example = "14.99", position = 5)
  private String price;

  @ApiModelProperty(example = "1", position = 6)
  private int quantity;

  @ApiModelProperty(example = "arc-d-triomphe.gif", position = 7)
  private String image;

  @ApiModelProperty(example = "14.99", position = 8)
  private String subtotal;
}
