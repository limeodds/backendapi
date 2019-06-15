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
public class ProductReviewDto {
  @ApiModelProperty(example = "Eder Taveira", position = 1)
  private String name;

  @ApiModelProperty(example = "That's a good product. The best for me.", position = 2)
  private String review;

  @ApiModelProperty(example = "5", position = 3)
  private int rating;

  @ApiModelProperty(example = "2019-02-17 13:57:29", position = 4)
  private String created_on;
}
