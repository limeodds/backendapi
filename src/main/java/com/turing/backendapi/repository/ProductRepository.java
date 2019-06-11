package com.turing.backendapi.repository;

import com.turing.backendapi.repository.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Integer> {
  Page<ProductEntity> findAll(Pageable pageable);

}
