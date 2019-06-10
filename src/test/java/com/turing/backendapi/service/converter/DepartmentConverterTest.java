package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.Department;
import com.turing.backendapi.repository.entity.DepartmentEntity;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class DepartmentConverterTest {

  @Test
  public void toDb() {
    //given
    Department domainObj = new Department(123, "nameTest", "descriptionTst");

    //when
    DepartmentEntity entityObj = DepartmentConverter.toDb(domainObj);

    //then
    assertThat(entityObj.getDepartment_id()).isEqualTo(domainObj.getDepartment_id());
    assertThat(entityObj.getName()).isEqualTo(domainObj.getName());
    assertThat(entityObj.getDescription()).isEqualTo(domainObj.getDescription());

  }

  @Test
  public void toDomain() {
    //given
    DepartmentEntity entityObj = new DepartmentEntity(123, "nameTest", "descriptionTst");

    //when
    Department domainObj = DepartmentConverter.toDomain(entityObj);

    //then
    assertThat(domainObj.getDepartment_id()).isEqualTo(entityObj.getDepartment_id());
    assertThat(domainObj.getName()).isEqualTo(entityObj.getName());
    assertThat(domainObj.getDescription()).isEqualTo(entityObj.getDescription());
  }
}