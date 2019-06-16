package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.DtoUtils;
import com.turing.backendapi.controller.dto.OrderDetailsDto;
import com.turing.backendapi.controller.dto.OrderShortDetailsDto;
import com.turing.backendapi.domain.OrderDetails;
import com.turing.backendapi.domain.OrderShortDetails;

public class OrderDtoConverter {

  public static OrderDetailsDto toDto(OrderDetails domainObj) {
    if (domainObj == null) {
      return null;
    }
    return OrderDetailsDto.builder()
                          .order_id(domainObj.getOrder_id())
                          .product_id(domainObj.getProduct_id())
                          .attributes(domainObj.getAttributes())
                          .product_name(domainObj.getProduct_name())
                          .quantity(domainObj.getQuantity())
                          .unit_cost(DtoUtils.format(domainObj.getUnit_cost()))
                          .subtotal(DtoUtils.format(domainObj.getSubtotal()))
                          .build();
  }

  public static OrderShortDetailsDto toDto(OrderShortDetails domainObj) {
    if (domainObj == null) {
      return null;
    }
    return OrderShortDetailsDto.builder()
                               .order_id(domainObj.getOrder_id())
                               .total_amount(DtoUtils.format(domainObj.getTotal_amount()))
                               .created_on(DtoUtils.format(domainObj.getCreated_on()))
                               .shipped_on(DtoUtils.format(domainObj.getShipped_on()))
                               .status(domainObj.getStatus())
                               .name(domainObj.getName())
                               .build();
  }

}