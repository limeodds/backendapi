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
public class CartProductSavedDto {
  @ApiModelProperty(example = "2", position = 1)
  private int item_id;

  @ApiModelProperty(example = "Arc d'Triomphe", position = 2)
  private String name;

  @ApiModelProperty(example = "LG, red", position = 3)
  private String attributes;

  @ApiModelProperty(example = "14.99", position = 5)
  private String price;
}
