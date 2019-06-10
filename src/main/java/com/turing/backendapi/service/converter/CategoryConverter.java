package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.Category;
import com.turing.backendapi.repository.entity.CategoryEntity;

public class CategoryConverter {

    public static CategoryEntity toDb(Category domainObj) {
        if (domainObj == null) {
            return null;
        }
        return CategoryEntity.builder()
            .category_id(domainObj.getCategory_id())
            .department_id(domainObj.getDepartment_id())
            .name(domainObj.getName())
            .description(domainObj.getDescription())
            .build();

    }

    public static Category toDomain(CategoryEntity entityObj) {
        if (entityObj == null) {
            return null;
        }
        return Category.builder()
            .category_id(entityObj.getCategory_id())
            .department_id(entityObj.getDepartment_id())
            .name(entityObj.getName())
            .description(entityObj.getDescription())
            .build();

    }
}
