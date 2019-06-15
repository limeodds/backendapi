package com.turing.backendapi.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoCategoryPage {
    @ApiModelProperty(example = "40", position = 1)
    private long count;

    @ApiModelProperty(position = 2)
    private List<CategoryDto> rows;
}
