package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.Attribute;
import com.turing.backendapi.repository.entity.AttributeEntity;

public class AttributeConverter {

  public static AttributeEntity toDb(Attribute domainObj) {
    if (domainObj == null) {
      return null;
    }
    return AttributeEntity.builder()
                          .attribute_id(domainObj.getAttribute_id())
                          .name(domainObj.getName())
                          .build();

  }

  public static Attribute toDomain(AttributeEntity entityObj) {
    if (entityObj == null) {
      return null;
    }
    return Attribute.builder()
                    .attribute_id(entityObj.getAttribute_id())
                    .name(entityObj.getName())
                    .build();

  }
}
