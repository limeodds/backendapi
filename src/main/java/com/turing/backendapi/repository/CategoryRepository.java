package com.turing.backendapi.repository;

import com.turing.backendapi.repository.entity.CategoryEntity;
import com.turing.backendapi.repository.entity.DepartmentEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Integer> {
    Page<CategoryEntity> findAll(Pageable pageable);

    @Query(nativeQuery = true,value = "call catalog_get_categories_for_product(:inProductId)")
    List<Object> findInProduct(@Param("inProductId") int inProductId);
}
