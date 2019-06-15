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
public class CartIdDto {
  @ApiModelProperty(example = "mu1js99v9n1", position = 1)
  private String cart_id;
}
