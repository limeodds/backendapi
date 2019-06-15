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
public class ShippingRegionDto {
  @ApiModelProperty(example = "2", position = 1)
  private int shipping_region_id;

  @ApiModelProperty(example = "Please Select", position = 2)
  private String shipping_region;
}
