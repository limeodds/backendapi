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
public class TaxDto {
  @ApiModelProperty(example = "1", position = 1)
  private int tax_id;

  @ApiModelProperty(example = "Sales Tax at 8.5%", position = 2)
  private String tax_type;

  @ApiModelProperty(example = "8.50", position = 3)
  private String tax_percentage;
}
