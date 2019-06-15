package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.ShippingRegionDto;
import com.turing.backendapi.domain.ShippingRegion;

public class ShippingRegionDtoConverter {

  public static ShippingRegionDto toDto(ShippingRegion domainObj) {
    if (domainObj == null) {
      return null;
    }
    return ShippingRegionDto.builder()
                            .shipping_region_id(domainObj.getShipping_region_id())
                            .shipping_region(domainObj.getShipping_region())
                            .build();
  }

}
