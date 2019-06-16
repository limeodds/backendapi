package com.turing.backendapi.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
@AllArgsConstructor
@Builder
public class NotFoundException extends RuntimeException {
  private final String message;

}