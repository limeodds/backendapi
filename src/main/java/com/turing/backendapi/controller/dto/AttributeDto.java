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
public class AttributeDto {
  @ApiModelProperty(example = "1", position = 1)
  private int attribute_id;

  @ApiModelProperty(example = "Size", position = 2)
  private String name;
}
