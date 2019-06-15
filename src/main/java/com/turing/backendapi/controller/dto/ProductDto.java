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
public class ProductDto {
  @ApiModelProperty(example = "2", position = 1)
  private int product_id;

  @ApiModelProperty(example = "Chartres Cathedral", position = 2)
  private String name;

  @ApiModelProperty(example = "The Fur Merchants. Not all the beautiful stained glass in the great cathedrals depicts saints and angels! Lay aside your furs for the summer and wear this beautiful T-shirt!", position = 3)
  private String description;

  @ApiModelProperty(example = "16.95", position = 4)
  private String price;

  @ApiModelProperty(example = "15.95", position = 5)
  private String discounted_price;

  @ApiModelProperty(example = "chartres-cathedral-thumbnail.gif", position = 6)
  private String thumbnail;
}
