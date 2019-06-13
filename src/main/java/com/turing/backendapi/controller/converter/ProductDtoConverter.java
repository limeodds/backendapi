package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.DtoUtils;
import com.turing.backendapi.controller.dto.ProductAllFieldsDto;
import com.turing.backendapi.controller.dto.ProductDto;
import com.turing.backendapi.domain.Product;
import org.springframework.util.StringUtils;

public class ProductDtoConverter {

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
            .price(DtoUtils.format(domainObj.getPrice()))
            .discounted_price(DtoUtils.format(domainObj.getDiscounted_price()))
            .thumbnail(domainObj.getThumbnail())
            .build();
    }

    public static ProductAllFieldsDto toDtoAllFields(Product domainObj) {
        if (domainObj == null) {
            return null;
        }

        return ProductAllFieldsDto.builder()
            .product_id(domainObj.getProduct_id())
            .name(domainObj.getName())
            .description(domainObj.getDescription())
            .price(DtoUtils.format(domainObj.getPrice()))
            .discounted_price(DtoUtils.format(domainObj.getDiscounted_price()))
            .image(domainObj.getImage())
            .image_2(domainObj.getImage_2())
            .thumbnail(domainObj.getThumbnail())
            .display(domainObj.getDisplay())
            .build();
    }
}
