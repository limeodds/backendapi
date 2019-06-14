package com.turing.backendapi.controller.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor @Builder
public class UnauthorizedResponse {
  @ApiModelProperty(example = "AUT_02", position = 1)
  private final String code;

  @ApiModelProperty(example = "The apikey is invalid.", position = 2)
  private final String message;

  @ApiModelProperty(example = "API-KEY", position = 3)
  private final String field;

}
