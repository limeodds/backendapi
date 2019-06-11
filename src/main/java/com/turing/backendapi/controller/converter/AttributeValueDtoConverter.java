package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.AttributeValueDto;
import com.turing.backendapi.domain.AttributeValue;

public class AttributeValueDtoConverter {

  public static AttributeValueDto toDto(AttributeValue domainObj) {
    if (domainObj == null) {
      return null;
    }
    return AttributeValueDto.builder()
                            .attribute_value_id(domainObj.getAttribute_value_id())
                            .value(domainObj.getValue())
                            .build();

  }

  public static AttributeValue toDomain(AttributeValueDto entityObj) {
    if (entityObj == null) {
      return null;
    }
    return AttributeValue.builder()
                         .attribute_value_id(entityObj.getAttribute_value_id())
                         .value(entityObj.getValue())
                         .build();

  }

}
