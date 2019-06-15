package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.DtoUtils;
import com.turing.backendapi.controller.dto.ShippingDto;
import com.turing.backendapi.domain.Shipping;

public class ShippingDtoConverter {

  public static ShippingDto toDto(Shipping domainObj) {
    if (domainObj == null) {
      return null;
    }
    return ShippingDto.builder()
                      .shipping_id(domainObj.getShipping_id())
                      .shipping_type(domainObj.getShipping_type())
                      .shipping_cost(DtoUtils.format(domainObj.getShipping_cost()))
                      .shipping_region_id(domainObj.getShipping_region_id())
                      .build();

  }

}
