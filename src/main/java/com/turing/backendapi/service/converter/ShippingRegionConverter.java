package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.ShippingRegion;
import com.turing.backendapi.repository.entity.ShippingRegionEntity;

public class ShippingRegionConverter {

  public static ShippingRegionEntity toDb(ShippingRegion domainObj) {
    if (domainObj == null) {
      return null;
    }
    return ShippingRegionEntity.builder()
                         .shipping_region_id(domainObj.getShipping_region_id())
                         .shipping_region(domainObj.getShipping_region())
                         .build();

  }

  public static ShippingRegion toDomain(ShippingRegionEntity entityObj) {
    if (entityObj == null) {
      return null;
    }
    return ShippingRegion.builder()
                   .shipping_region_id(entityObj.getShipping_region_id())
                   .shipping_region(entityObj.getShipping_region())
                   .build();

  }
}
