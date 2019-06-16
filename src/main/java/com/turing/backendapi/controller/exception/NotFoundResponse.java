package com.turing.backendapi.controller.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor @Builder
public class NotFoundResponse {
  @ApiModelProperty(example = "Endpoint not found.", position = 1)
  private final String message;

}
