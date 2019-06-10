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
public class CategoryDto {

    @ApiModelProperty(value = "1")
    private int category_id;

    @ApiModelProperty(value = "1")
    private int department_id;

    @ApiModelProperty(value = "French")
    private String name;

    @ApiModelProperty(value = "The French have always had an eye for beauty. One look at the T-shirts below and you'll see that same appreciation has been applied abundantly to their postage stamps. Below are some of our most beautiful and colorful T-shirts, so browse away! And don't forget to go all the way to the bottom - you don't want to miss any of them!")
    private String description;
}
