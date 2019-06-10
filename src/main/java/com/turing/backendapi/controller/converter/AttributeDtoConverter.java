package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.AttributeDto;
import com.turing.backendapi.domain.Attribute;

public class AttributeDtoConverter {

  public static AttributeDto toDto(Attribute domainObj) {
    if (domainObj == null) {
      return null;
    }
    return AttributeDto.builder()
                       .attribute_id(domainObj.getAttribute_id())
                       .name(domainObj.getName())
                       .build();

  }

  public static Attribute toDomain(AttributeDto entityObj) {
    if (entityObj == null) {
      return null;
    }
    return Attribute.builder()
                    .attribute_id(entityObj.getAttribute_id())
                    .name(entityObj.getName())
                    .build();

  }

}
