package com.turing.backendapi.repository;

import com.turing.backendapi.repository.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Integer> {
  @Modifying
  @Query(nativeQuery = true, value = "call shopping_cart_add_product(:inCartId, :inProductId, :inAttributes)")
  void addProduct(@Param("inCartId") String inCartId,
                  @Param("inProductId") int inProductId,
                  @Param("inAttributes") String inAttributes);


  @Query(nativeQuery = true, value = "call shopping_cart_get_products(:inCartId)")
  List<Object[]> findProducts(@Param("inCartId") String inCartId);

  @Modifying
  @Query(nativeQuery = true, value = "call shopping_cart_update(:inItemId, :inQuantity)")
  void updateCart(@Param("inItemId") int inItemId,
                  @Param("inQuantity") int inQuantity);

  @Query("select t.cart_id from ShoppingCartEntity t where t.item_id = ?1")
  String findCartIdByItemId(Integer itemId);

  @Modifying
  @Query(nativeQuery = true, value = "call shopping_cart_empty(:inCartId)")
  void emptyShopingCart(@Param("inCartId") String inCartId);

  @Modifying
  @Query(nativeQuery = true, value = "call shopping_cart_move_product_to_cart(:inItemId)")
  void moveProductToCart(@Param("inItemId") Integer inItemId);

  @Query(nativeQuery = true, value = "call shopping_cart_get_total_amount(:inCartId)")
  BigDecimal getTotalAmount(@Param("inCartId") String inCartId);

  @Modifying
  @Query(nativeQuery = true, value = "call shopping_cart_save_product_for_later(:inItemId)")
  void saveProductForLater(@Param("inItemId") Integer inItemId);

  @Query(nativeQuery = true, value = "call shopping_cart_get_saved_products(:inCartId)")
  List<Object[]> findSavedProducts(@Param("inCartId") String inCartId);

  @Modifying
  @Query(nativeQuery = true, value = "call shopping_cart_remove_product(:inItemId)")
  void removeProduct(@Param("inItemId") Integer inItemId);
}
