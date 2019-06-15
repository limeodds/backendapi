package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.Shipping;
import com.turing.backendapi.repository.entity.ShippingEntity;

public class ShippingConverter {

  public static ShippingEntity toDb(Shipping domainObj) {
    if (domainObj == null) {
      return null;
    }
    return ShippingEntity.builder()
                         .shipping_id(domainObj.getShipping_id())
                         .shipping_type(domainObj.getShipping_type())
                         .shipping_cost(domainObj.getShipping_cost())
                         .shipping_region_id(domainObj.getShipping_region_id())
                         .build();

  }

  public static Shipping toDomain(ShippingEntity entityObj) {
    if (entityObj == null) {
      return null;
    }
    return Shipping.builder()
                   .shipping_id(entityObj.getShipping_id())
                   .shipping_type(entityObj.getShipping_type())
                   .shipping_cost(entityObj.getShipping_cost())
                   .shipping_region_id(entityObj.getShipping_region_id())
                   .build();

  }
}
