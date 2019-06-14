package com.turing.backendapi.repository;

import com.turing.backendapi.repository.entity.OrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    @Modifying
    @Query(nativeQuery = true, value = "call shopping_cart_create_order(:inCartId, :inCustomerId, :inShippingId, :inTaxId)")
    Integer createOrder(@Param("inCartId") String inCartId,
        @Param("inCustomerId") int inCustomerId,
        @Param("inShippingId") int inShippingId,
        @Param("inTaxId") int inTaxId);
}
