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
public class ShippingDto {
  @ApiModelProperty(example = "1", position = 1)
  private int shipping_id;

  @ApiModelProperty(example = "Next Day Delivery ($20)", position = 2)
  private String shipping_type;

  @ApiModelProperty(example = "20.00", position = 3)
  private String shipping_cost;

  @ApiModelProperty(example = "2", position = 4)
  private int shipping_region_id;
}
