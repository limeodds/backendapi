package com.turing.backendapi.controller.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAllFieldsDto {
  @ApiModelProperty(example = "2", position = 1)
  private int product_id;

  @ApiModelProperty(example = "Chartres Cathedral", position = 2)
  private String name;

  @ApiModelProperty(example = "Lay aside your furs for the summer and wear this beautiful T-shirt!", position = 3)
  private String description;

  @ApiModelProperty(example = "16.95", position = 4)
  private String price;

  @ApiModelProperty(example = "15.95", position = 5)
  private String discounted_price;

  @ApiModelProperty(example = "chartres-cathedral.gif", position = 6)
  private String image;

  @ApiModelProperty(example = "chartres-cathedral2.gif", position = 7)
  private String image_2;

  @ApiModelProperty(example = "chartres-cathedral-thumbnail.gif", position = 8)
  private String thumbnail;

  @ApiModelProperty(example = "0", position = 9)
  private int display;
}
