package com.turing.backendapi.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {
  //Department's Errors
  DEP_01("DEP_01", "The ID is not a number."),
  DEP_02("DEP_02", "Don'exist department with this ID.");

  private final String code;
  private final String description;

}
