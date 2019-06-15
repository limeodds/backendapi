package com.turing.backendapi.controller.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DtoPage <T> {
    @ApiModelProperty(example = "40", position = 1)
    private long count;

    @ApiModelProperty(position = 2)
    private List<T> rows;
}
