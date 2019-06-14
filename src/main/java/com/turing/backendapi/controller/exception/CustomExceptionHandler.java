package com.turing.backendapi.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public final ResponseEntity<Map> handleNotFoundException(BadRequestException ex, WebRequest request) {
    ErrorResponse errorResponse = ErrorResponse.builder()
                                               .status(HttpStatus.BAD_REQUEST.value())
                                               .code(ex.getCode())
                                               .message(ex.getMessage())
                                               .field(ex.getField())
                                               .build();
    return new ResponseEntity<>(Map.of("error", errorResponse), HttpStatus.BAD_REQUEST);
  }

}
