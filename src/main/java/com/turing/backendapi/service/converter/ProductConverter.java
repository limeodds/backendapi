package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.Product;
import com.turing.backendapi.repository.entity.ProductEntity;

public class ProductConverter {

    public static ProductEntity toDb(Product domainObj) {
        if (domainObj == null) {
            return null;
        }
        return ProductEntity.builder()
            .product_id(domainObj.getProduct_id())
            .name(domainObj.getName())
            .description(domainObj.getDescription())
            .price(domainObj.getPrice())
            .discounted_price(domainObj.getDiscounted_price())
            .image(domainObj.getImage())
            .image_2(domainObj.getImage_2())
            .thumbnail(domainObj.getThumbnail())
            .display(domainObj.getDisplay())
            .build();

    }

    public static Product toDomain(ProductEntity entityObj) {
        if (entityObj == null) {
            return null;
        }
        return Product.builder()
            .product_id(entityObj.getProduct_id())
            .name(entityObj.getName())
            .description(entityObj.getDescription())
            .price(entityObj.getPrice())
            .discounted_price(entityObj.getDiscounted_price())
            .image(entityObj.getImage())
            .image_2(entityObj.getImage_2())
            .thumbnail(entityObj.getThumbnail())
            .display(entityObj.getDisplay())
            .build();

    }
}
