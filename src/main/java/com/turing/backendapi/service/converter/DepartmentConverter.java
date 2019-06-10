package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.Department;
import com.turing.backendapi.repository.entity.DepartmentEntity;

public class DepartmentConverter {
  public static DepartmentEntity toDb(Department domainObj) {
    if (domainObj == null) {
      return null;
    }
    return DepartmentEntity.builder()
                           .department_id(domainObj.getDepartment_id())
                           .name(domainObj.getName())
                           .description((domainObj.getDescription()))
                           .build();

  }

  public static Department toDomain(DepartmentEntity entityObj) {
    if (entityObj == null) {
      return null;
    }
    return Department.builder()
                           .department_id(entityObj.getDepartment_id())
                           .name(entityObj.getName())
                           .description((entityObj.getDescription()))
                           .build();

  }
}
