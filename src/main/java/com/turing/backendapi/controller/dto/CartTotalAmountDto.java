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
public class CartTotalAmountDto {
  @ApiModelProperty(example = "23.45", position = 1)
  private String total_amount;
}
