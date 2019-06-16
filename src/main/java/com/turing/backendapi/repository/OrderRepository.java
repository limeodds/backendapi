package com.turing.backendapi.repository;

import com.turing.backendapi.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
  @Query(nativeQuery = true, value = "call shopping_cart_create_order(:inCartId, :inCustomerId, :inShippingId, :inTaxId)")
  Integer createOrder(@Param("inCartId") String inCartId,
                      @Param("inCustomerId") int inCustomerId,
                      @Param("inShippingId") int inShippingId,
                      @Param("inTaxId") int inTaxId);

  @Query(nativeQuery = true, value = "call orders_get_order_details(:inOrderId)")
  List<Object[]> getOrderDetails(@Param("inOrderId") int inOrderId);

  @Query(nativeQuery = true, value = "call orders_get_by_customer_id(:inCustomerId)")
  List<Object[]> getOrdersByCustomer(@Param("inCustomerId") int inCustomerId);

  @Query(nativeQuery = true, value = "call orders_get_order_short_details(:inOrderId)")
  List<Object[]> getOrderShortDetails(@Param("inOrderId") int inOrderId);
}
