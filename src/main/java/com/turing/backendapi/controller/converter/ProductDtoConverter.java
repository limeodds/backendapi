package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.ProductDto;
import com.turing.backendapi.domain.Product;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;

public class ProductDtoConverter {
  public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,##0.##");

  public static ProductDto toDto(Product domainObj, int descriprionMaxLength) {
    if (domainObj == null) {
      return null;
    }

    String description = domainObj.getDescription();

    if (!StringUtils.isEmpty(description) && description.length() > descriprionMaxLength) {
      description = description.substring(0, descriprionMaxLength);
    }

    return ProductDto.builder()
                     .product_id(domainObj.getProduct_id())
                     .name(domainObj.getName())
                     .description(description)
                     .price(domainObj.getPrice() != null ? DECIMAL_FORMAT.format(domainObj.getPrice()) : null)
                     .discounted_price(domainObj.getDiscounted_price() != null ? DECIMAL_FORMAT.format(domainObj.getDiscounted_price()) : null)
                     .thumbnail(domainObj.getThumbnail())
                     .build();


  }


}
