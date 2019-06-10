package com.turing.backendapi.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Data
@AllArgsConstructor
@Builder
public class BadRequestException extends RuntimeException {
  private final String code;
  private final String message;
  private final String field;

}