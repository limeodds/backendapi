package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.AttributeValue;
import com.turing.backendapi.repository.entity.AttributeValueEntity;

public class AttributeValueConverter {

  public static AttributeValueEntity toDb(AttributeValue domainObj) {
    if (domainObj == null) {
      return null;
    }
    return AttributeValueEntity.builder()
                               .attribute_value_id(domainObj.getAttribute_value_id())
                               .attribute_id(domainObj.getAttribute_id())
                               .value(domainObj.getValue())
                               .build();

  }

  public static AttributeValue toDomain(AttributeValueEntity entityObj) {
    if (entityObj == null) {
      return null;
    }
    return AttributeValue.builder()
                         .attribute_value_id(entityObj.getAttribute_value_id())
                         .attribute_id(entityObj.getAttribute_id())
                         .value(entityObj.getValue())
                         .build();

  }
}
