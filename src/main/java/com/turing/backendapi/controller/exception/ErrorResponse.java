package com.turing.backendapi.controller.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor @Builder
public class ErrorResponse {
  @ApiModelProperty(example = "USR_02", position = 1)
  private final String code;

  @ApiModelProperty(example = "The field example is empty.", position = 2)
  private final String message;

  @ApiModelProperty(example = "example", position = 3)
  private final String field;

  @ApiModelProperty(example = "500", position = 4)
  private final int status;

}
