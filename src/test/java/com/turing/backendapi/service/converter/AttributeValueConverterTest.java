package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.AttributeValue;
import com.turing.backendapi.repository.entity.AttributeValueEntity;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AttributeValueConverterTest {
  @Test
  public void toDb() {
    //given
    AttributeValue domainObj = new AttributeValue(123, 456, "valueTest");

    //when
    AttributeValueEntity entityObj = AttributeValueConverter.toDb(domainObj);

    //then
    assertThat(entityObj.getAttribute_value_id()).isEqualTo(domainObj.getAttribute_value_id());
    assertThat(entityObj.getAttribute_id()).isEqualTo(domainObj.getAttribute_id());
    assertThat(entityObj.getValue()).isEqualTo(domainObj.getValue());

  }

  @Test
  public void toDomain() {
    //given
    AttributeValueEntity entityObj = new AttributeValueEntity(123, 456, "valueTest");

    //when
    AttributeValue domainObj = AttributeValueConverter.toDomain(entityObj);

    //then
    assertThat(domainObj.getAttribute_value_id()).isEqualTo(entityObj.getAttribute_value_id());
    assertThat(domainObj.getAttribute_id()).isEqualTo(entityObj.getAttribute_id());
    assertThat(domainObj.getValue()).isEqualTo(entityObj.getValue());
  }
}