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
public class LoginResponseDto {
  @ApiModelProperty(position = 1)
  private Customer customer;

  @ApiModelProperty(position = 2, example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoiY3VzdG9tZXIiLCJpYXQiOjE1NTA0MjQ0OTgsImV4cCI6MTU1MDUxMDg5OH0.aEFrNUPRWuRWx0IOEL-_A4J4Ti39iXEHAScm6GI61RR")
  private String accessToken;

  @ApiModelProperty(example = "24h", position = 3)
  private String expires_in;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Customer {
    @ApiModelProperty
    private CustomerDto schema;
  }
}
