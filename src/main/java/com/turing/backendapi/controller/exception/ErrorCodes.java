package com.turing.backendapi.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {
  //General Errors
  GEN_01("GEN_01", "The field(s) are/is required."),

  //Authentication's Errors
  AUT_01("AUT_01", "Authorization code is empty."),
  AUT_02("AUT_02", "Access Unauthorized."),

  //Pagination's Errors
  PAG_01("PAG_01", "The order is not matched 'field,(DESC|ASC)'."),
  PAG_02("PAG_02", "The field of order is not allow sorting."),
  PAG_03("PAG_03", "Invalid pagination parameter. Must be an integer >0."),


  //User's Errors
  USR_01("USR_01", "Email or Password is invalid."),
  USR_02("USR_02", "The field(s) are/is required."),
  USR_03("USR_03", "The email is invalid."),
  USR_04("USR_04", "The email already exists."),
  USR_05("USR_05", "The email doesn't exist."),
  USR_06("USR_06", "this is an invalid phone number."),
  USR_07("USR_07", "this is too long <FIELD NAME>."),
  USR_08("USR_08", "this is an invalid Credit Card."),
  USR_09("USR_09", "The Shipping Region ID is not number"),

  //Category's Errors
  CAT_01("CAT_01", "Don't exist category with this ID."),
  CAT_02("CAT_02", "The field(s) are/is required."),

  //Department's Errors
  DEP_01("DEP_01", "The ID is not a number."),
  DEP_02("DEP_02", "Don'exist department with this ID."),

  //Attribute's Errors
  ATR_01("ATR_01", "The ID is not a number."),
  ATR_02("ATR_02", "Don'exist attribute with this ID."),

  //Product's Errors
  PRD_01("PRD_01", "Don'exist product with this ID."),

  //Tax's Errors
  TAX_01("TAX_01", "Don'exist tax with this ID.");

  private final String code;
  private final String description;

}
