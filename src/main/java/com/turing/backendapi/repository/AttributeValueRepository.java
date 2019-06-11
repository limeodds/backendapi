package com.turing.backendapi.repository;

import com.turing.backendapi.repository.entity.AttributeValueEntity;
import com.turing.backendapi.repository.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValueEntity, Integer> {

  @Query("select t from AttributeValueEntity t where t.attribute_id = ?1")
  List<AttributeValueEntity> findByAttributeId(Integer attribute_id);

  @Query(nativeQuery = true,value = "call catalog_get_product_attributes(:inProductId)")
  List<Object> findByProductId(@Param("inProductId") int inProductId);
}
