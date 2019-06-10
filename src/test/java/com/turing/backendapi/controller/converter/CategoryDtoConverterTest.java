package com.turing.backendapi.controller.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.turing.backendapi.controller.dto.CategoryDto;
import com.turing.backendapi.domain.Category;
import org.junit.Test;

public class CategoryDtoConverterTest {

    @Test
    public void toDto() {
        //given
        Category domainObj = new Category(123, 456, "nameTest", "descriptionTst");

        //when
        CategoryDto dtoObj = CategoryDtoConverter.toDto(domainObj);

        //then
        assertThat(dtoObj.getCategory_id()).isEqualTo(domainObj.getCategory_id());
        assertThat(dtoObj.getDepartment_id()).isEqualTo(domainObj.getDepartment_id());
        assertThat(dtoObj.getName()).isEqualTo(domainObj.getName());
        assertThat(dtoObj.getDescription()).isEqualTo(domainObj.getDescription());

    }

    @Test
    public void toDomain() {
        //given
        CategoryDto dtoObj = new CategoryDto(123, 456, "nameTest", "descriptionTst");

        //when
        Category domainObj = CategoryDtoConverter.toDomain(dtoObj);

        //then
        assertThat(domainObj.getCategory_id()).isEqualTo(dtoObj.getCategory_id());
        assertThat(domainObj.getDepartment_id()).isEqualTo(dtoObj.getDepartment_id());
        assertThat(domainObj.getName()).isEqualTo(dtoObj.getName());
        assertThat(domainObj.getDescription()).isEqualTo(dtoObj.getDescription());
    }

}