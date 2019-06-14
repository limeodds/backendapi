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
public class CustomerDto {
  @ApiModelProperty(example = "1", position = 1)
  private int customer_id;

  @ApiModelProperty(example = "Lannucci", position = 2)
  private String name;

  @ApiModelProperty(example = "lannucci@hotmail.com", position = 3)
  private String email;

  @ApiModelProperty(example = "QI 19", position = 4)
  private String address_1;

  @ApiModelProperty(example = "", position = 5)
  private String address_2;

  @ApiModelProperty(example = "", position = 6)
  private String city;

  @ApiModelProperty(example = "", position = 7)
  private String region;

  @ApiModelProperty(example = "", position = 8)
  private String postal_code;

  @ApiModelProperty(example = "", position = 9)
  private String country;

  @ApiModelProperty(example = "1", position = 10)
  private int shipping_region_id;

  @ApiModelProperty(example = "+351323213511235", position = 11)
  private String day_phone;

  @ApiModelProperty(example = "+452436143246123", position = 12)
  private String eve_phone;

  @ApiModelProperty(example = "+351323213511235", position = 13)
  private String mob_phone;

  @ApiModelProperty(example = "XXXXXXXX5100", position = 14)
  private String credit_card;
}
