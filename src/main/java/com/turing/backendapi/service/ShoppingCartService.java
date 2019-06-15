package com.turing.backendapi.service;

import com.turing.backendapi.domain.CartProduct;
import com.turing.backendapi.repository.ShoppingCartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;


@Service
@Transactional(readOnly = true)
@Slf4j
public class ShoppingCartService {

  private final ShoppingCartRepository shoppingCartRepository;

  @Autowired
  public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
    this.shoppingCartRepository = shoppingCartRepository;
  }

  public String generateUniqueId() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  @Transactional
  public void addProduct(String cartId, int productId, String attributes) {
    shoppingCartRepository.addProduct(cartId, productId, attributes);
  }

  public List<CartProduct> getProducts(String cartId) {
    return shoppingCartRepository.findProducts(cartId).stream()
                                 .map(o -> CartProduct.builder()
                                                      .item_id((Integer) o[0])
                                                      .name((String) o[1])
                                                      .attributes((String) o[2])
                                                      .price((BigDecimal) o[3])
                                                      .quantity((Integer) o[4])
                                                      .subtotal((BigDecimal) o[5])
                                                      .product_id((Integer) o[6])
                                                      .image((String) o[7])
                                                      .build())
                                 .collect(toList());
  }

  @Transactional
  public void updateCart(int itemId, int quantity) {
    shoppingCartRepository.updateCart(itemId, quantity);
  }

  public String getCartIdByItemId(Integer itemId) {
    return shoppingCartRepository.findCartIdByItemId(itemId);
  }

  @Transactional
  public void emptyShopingCart(String cartId) {
    shoppingCartRepository.emptyShopingCart(cartId);

  }

  @Transactional
  public void moveProductToCart(Integer itemId) {
    shoppingCartRepository.moveProductToCart(itemId);
  }

  public BigDecimal getTotalAmount(String cartId) {
    return shoppingCartRepository.getTotalAmount(cartId);
  }

  @Transactional
  public void saveProductForLater(Integer itemId) {
    shoppingCartRepository.saveProductForLater(itemId);
  }

  public List<CartProduct> getSavedProducts(String cartId) {
    return shoppingCartRepository.findSavedProducts(cartId).stream()
                                 .map(o -> CartProduct.builder()
                                                      .item_id((Integer) o[0])
                                                      .name((String) o[1])
                                                      .attributes((String) o[2])
                                                      .price((BigDecimal) o[3])
                                                      .build())
                                 .collect(toList());
  }

  @Transactional
  public void removeProduct(Integer itemId) {
    shoppingCartRepository.removeProduct(itemId);
  }
}
