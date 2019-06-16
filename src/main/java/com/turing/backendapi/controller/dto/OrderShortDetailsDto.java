package com.turing.backendapi.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderShortDetailsDto {
  @ApiModelProperty(example = "1", position =1 )
  private int order_id;

  @ApiModelProperty(example = "123.56", position =2 )
  private String total_amount;

  @ApiModelProperty(example = "", position = 3)
  private String created_on;

  @ApiModelProperty(example = "", position =4 )
  private String shipped_on;

  @ApiModelProperty(example = "1", position = 5)
  private int status;

  @ApiModelProperty(example = "Test", position =6 )
  private String name;
}
