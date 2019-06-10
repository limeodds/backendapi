package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.CategoryDto;
import com.turing.backendapi.domain.Category;

public class CategoryDtoConverter {

    public static CategoryDto toDto(Category domainObj) {
        if (domainObj == null) {
            return null;
        }
        return CategoryDto.builder()
            .category_id(domainObj.getCategory_id())
            .department_id(domainObj.getDepartment_id())
            .name(domainObj.getName())
            .description((domainObj.getDescription()))
            .build();

    }

    public static Category toDomain(CategoryDto entityObj) {
        if (entityObj == null) {
            return null;
        }
        return Category.builder()
            .category_id(entityObj.getCategory_id())
            .department_id(entityObj.getDepartment_id())
            .name(entityObj.getName())
            .description((entityObj.getDescription()))
            .build();

    }

}
