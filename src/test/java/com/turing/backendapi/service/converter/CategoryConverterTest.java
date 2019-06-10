package com.turing.backendapi.service.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import com.turing.backendapi.domain.Category;
import com.turing.backendapi.domain.Department;
import com.turing.backendapi.repository.entity.CategoryEntity;
import com.turing.backendapi.repository.entity.DepartmentEntity;
import org.junit.Test;

public class CategoryConverterTest {

    @Test
    public void toDb() {
        //given
        Category domainObj = new Category(123, 456, "nameTest", "descriptionTst");

        //when
        CategoryEntity entityObj = CategoryConverter.toDb(domainObj);

        //then
        assertThat(entityObj.getCategory_id()).isEqualTo(domainObj.getCategory_id());
        assertThat(entityObj.getDepartment_id()).isEqualTo(domainObj.getDepartment_id());
        assertThat(entityObj.getName()).isEqualTo(domainObj.getName());
        assertThat(entityObj.getDescription()).isEqualTo(domainObj.getDescription());

    }

    @Test
    public void toDomain() {
        //given
        CategoryEntity entityObj = new CategoryEntity(123, 456, "nameTest", "descriptionTst");

        //when
        Category domainObj = CategoryConverter.toDomain(entityObj);

        //then
        assertThat(domainObj.getCategory_id()).isEqualTo(entityObj.getCategory_id());
        assertThat(domainObj.getDepartment_id()).isEqualTo(entityObj.getDepartment_id());
        assertThat(domainObj.getName()).isEqualTo(entityObj.getName());
        assertThat(domainObj.getDescription()).isEqualTo(entityObj.getDescription());
    }
}