package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.DepartmentDto;
import com.turing.backendapi.domain.Department;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DepartmentDtoConverterTest {

  @Test
  public void toDto() {
    //given
    Department domainObj = new Department(123, "nameTest", "descriptionTst");

    //when
    DepartmentDto dtoObj = DepartmentDtoConverter.toDto(domainObj);

    //then
    assertThat(dtoObj.getDepartment_id()).isEqualTo(domainObj.getDepartment_id());
    assertThat(dtoObj.getName()).isEqualTo(domainObj.getName());
    assertThat(dtoObj.getDescription()).isEqualTo(domainObj.getDescription());

  }

  @Test
  public void toDomain() {
    //given
    DepartmentDto dtoObj = new DepartmentDto(123, "nameTest", "descriptionTst");

    //when
    Department domainObj = DepartmentDtoConverter.toDomain(dtoObj);

    //then
    assertThat(domainObj.getDepartment_id()).isEqualTo(dtoObj.getDepartment_id());
    assertThat(domainObj.getName()).isEqualTo(dtoObj.getName());
    assertThat(domainObj.getDescription()).isEqualTo(dtoObj.getDescription());
  }
}