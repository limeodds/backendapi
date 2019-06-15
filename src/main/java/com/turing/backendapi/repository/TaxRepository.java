package com.turing.backendapi.repository;

import com.turing.backendapi.repository.entity.AttributeEntity;
import com.turing.backendapi.repository.entity.TaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<TaxEntity, Integer> {

}
