package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.CartProductDto;
import com.turing.backendapi.controller.dto.DtoUtils;
import com.turing.backendapi.domain.CartProduct;

public class ShoppingCartDtoConverter {

  public static CartProductDto toDto(CartProduct domainObj) {
    if (domainObj == null) {
      return null;
    }
    return CartProductDto.builder()
                         .item_id(domainObj.getItem_id())
                         .name(domainObj.getName())
                         .attributes(domainObj.getAttributes())
                         .product_id(domainObj.getProduct_id())
                         .price(DtoUtils.format(domainObj.getPrice()))
                         .quantity(domainObj.getQuantity())
                         .image(domainObj.getImage())
                         .subtotal(DtoUtils.format(domainObj.getSubtotal()))
                         .build();
  }

}