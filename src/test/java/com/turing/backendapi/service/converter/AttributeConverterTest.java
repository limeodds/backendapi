package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.Attribute;
import com.turing.backendapi.repository.entity.AttributeEntity;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AttributeConverterTest {
  @Test
  public void toDb() {
    //given
    Attribute domainObj = new Attribute(123, "nameTest");

    //when
    AttributeEntity entityObj = AttributeConverter.toDb(domainObj);

    //then
    assertThat(entityObj.getAttribute_id()).isEqualTo(domainObj.getAttribute_id());
    assertThat(entityObj.getName()).isEqualTo(domainObj.getName());

  }

  @Test
  public void toDomain() {
    //given
    AttributeEntity entityObj = new AttributeEntity(123, "nameTest");

    //when
    Attribute domainObj = AttributeConverter.toDomain(entityObj);

    //then
    assertThat(domainObj.getAttribute_id()).isEqualTo(entityObj.getAttribute_id());
    assertThat(domainObj.getName()).isEqualTo(entityObj.getName());
  }

}