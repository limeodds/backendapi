package com.turing.backendapi.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor @Builder
public class ErrorResponse {
  private final int status;
  private final String code;
  private final String message;
  private final String field;

}
