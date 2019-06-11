package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.DepartmentDto;
import com.turing.backendapi.domain.Department;

public class DepartmentDtoConverter {
  public static DepartmentDto toDto(Department domainObj) {
    if (domainObj == null) {
      return null;
    }
    return DepartmentDto.builder()
                        .department_id(domainObj.getDepartment_id())
                        .name(domainObj.getName())
                        .description(domainObj.getDescription())
                        .build();

  }

  public static Department toDomain(DepartmentDto entityObj) {
    if (entityObj == null) {
      return null;
    }
    return Department.builder()
                     .department_id(entityObj.getDepartment_id())
                     .name(entityObj.getName())
                     .description(entityObj.getDescription())
                     .build();

  }

}
