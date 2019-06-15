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
public class DepartmentDto {
  @ApiModelProperty(example = "1", position = 1)
  private int department_id;

  @ApiModelProperty(example = "Regional", position = 2)
  private String name;

  @ApiModelProperty(example = "Proud of your country? Wear a T-shirt with a national symbol stamp!", position = 3)
  private String description;
}
