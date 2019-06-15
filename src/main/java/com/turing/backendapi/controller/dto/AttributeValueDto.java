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
public class AttributeValueDto {
  @ApiModelProperty(example = "1", position = 1)
  private int attribute_value_id;

  @ApiModelProperty(example = "5", position = 1)
  private String value;
}
