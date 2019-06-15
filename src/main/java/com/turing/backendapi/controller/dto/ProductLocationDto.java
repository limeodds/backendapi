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
public class ProductLocationDto {
    @ApiModelProperty(example = "1", position = 1)
    private int category_id;

    @ApiModelProperty(example = "French", position = 2)
    private String category_name;

    @ApiModelProperty(example = "1", position = 3)
    private int department_id;

    @ApiModelProperty(example = "Regional", position = 4)
    private String department_name;
}
