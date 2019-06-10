package com.turing.backendapi.service.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import com.turing.backendapi.domain.Category;
import com.turing.backendapi.domain.Product;
import com.turing.backendapi.repository.entity.CategoryEntity;
import com.turing.backendapi.repository.entity.ProductEntity;
import java.math.BigDecimal;
import org.junit.Test;

public class ProductConverterTest {

    @Test
    public void toDb() {
        //given
        Product domainObj = new Product(123, "nameTest", "descriptionTst", new BigDecimal(12.34), new BigDecimal(1.34), "image",
        "image_2", "thumbnail", 1);

        //when
        ProductEntity entityObj = ProductConverter.toDb(domainObj);

        //then
        assertThat(entityObj.getProduct_id()).isEqualTo(domainObj.getProduct_id());
        assertThat(entityObj.getName()).isEqualTo(domainObj.getName());
        assertThat(entityObj.getDescription()).isEqualTo(domainObj.getDescription());
        assertThat(entityObj.getPrice()).isEqualTo(domainObj.getPrice());
        assertThat(entityObj.getDiscounted_price()).isEqualTo(domainObj.getDiscounted_price());
        assertThat(entityObj.getImage()).isEqualTo(domainObj.getImage());
        assertThat(entityObj.getImage_2()).isEqualTo(domainObj.getImage_2());
        assertThat(entityObj.getThumbnail()).isEqualTo(domainObj.getThumbnail());
        assertThat(entityObj.getDisplay()).isEqualTo(domainObj.getDisplay());
    }

    @Test
    public void toDomain() {
        //given
        ProductEntity entityObj = new ProductEntity(123, "nameTest", "descriptionTst", new BigDecimal(12.34), new BigDecimal(1.34), "image",
            "image_2", "thumbnail", 1);

        //when
        Product domainObj = ProductConverter.toDomain(entityObj);

        //then
        assertThat(domainObj.getProduct_id()).isEqualTo(entityObj.getProduct_id());
        assertThat(domainObj.getName()).isEqualTo(entityObj.getName());
        assertThat(domainObj.getDescription()).isEqualTo(entityObj.getDescription());
        assertThat(domainObj.getPrice()).isEqualTo(entityObj.getPrice());
        assertThat(domainObj.getDiscounted_price()).isEqualTo(entityObj.getDiscounted_price());
        assertThat(domainObj.getImage()).isEqualTo(entityObj.getImage());
        assertThat(domainObj.getImage_2()).isEqualTo(entityObj.getImage_2());
        assertThat(domainObj.getThumbnail()).isEqualTo(entityObj.getThumbnail());
        assertThat(domainObj.getDisplay()).isEqualTo(entityObj.getDisplay());
    }
}