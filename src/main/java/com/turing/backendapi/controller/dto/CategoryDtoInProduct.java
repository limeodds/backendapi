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

    @ApiModelProperty(value="1")
    private int category_id;

    @ApiModelProperty(value="1")
    private int department_id;

    @ApiModelProperty(value="French")
    private String name;
}
