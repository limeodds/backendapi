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
  @ApiModelProperty(value="1")
  private int department_id;

  @ApiModelProperty(value="Regional")
  private String name;

  @ApiModelProperty(value="Proud of your country? Wear a T-shirt with a national symbol stamp!")
  private String description;
}
