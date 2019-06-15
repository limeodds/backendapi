package com.turing.backendapi.repository;

import com.turing.backendapi.repository.entity.ShippingEntity;
import com.turing.backendapi.repository.entity.ShippingRegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingRepository extends JpaRepository<ShippingEntity, Integer> {

  @Query("select t from ShippingEntity t where t.shipping_region_id = ?1")
  List<ShippingEntity> findByShippingRegionId(Integer shippigRegionId);

  @Query("select t from ShippingRegionEntity t")
  List<ShippingRegionEntity> findAllShippingRegions();

}
