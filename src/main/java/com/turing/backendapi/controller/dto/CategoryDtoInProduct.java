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
public class CategoryDtoInProduct {

    @ApiModelProperty(example = "1", position = 1)
    private int category_id;

    @ApiModelProperty(example = "1", position = 2)
    private int department_id;

    @ApiModelProperty(example = "Spain", position = 3)
    private String name;
}
