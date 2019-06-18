package com.turing.backendapi.service;

import com.turing.backendapi.domain.CartProduct;
import com.turing.backendapi.repository.ShoppingCartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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

  @Value("${shoppingcart.cleanup.interval}")
  private int cleanupInterval;

  private final ShoppingCartRepository shoppingCartRepository;

  @Autowired
  public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
    this.shoppingCartRepository = shoppingCartRepository;
  }

  public String generateUniqueId() {
    log.info("generateUniqueId()");
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  @Transactional
  public void addProduct(String cartId, int productId, String attributes) {
    log.info("addProduct(cartId: {}, productId: {}, attributes: {})", cartId, productId, attributes);
    shoppingCartRepository.addProduct(cartId, productId, attributes);
  }

  public List<CartProduct> getProducts(String cartId) {
    log.info("getProducts(cartId: {})", cartId);
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
    log.info("updateCart(itemId: {}, quantity: {})", itemId, quantity);
    shoppingCartRepository.updateCart(itemId, quantity);
  }

  public String getCartIdByItemId(Integer itemId) {
    log.info("getCartIdByItemId(itemId: {})", itemId);
    return shoppingCartRepository.findCartIdByItemId(itemId);
  }

  @Transactional
  public void emptyShopingCart(String cartId) {
    log.info("emptyShopingCart(cartId: {})", cartId);
    shoppingCartRepository.emptyShopingCart(cartId);
  }

  @Transactional
  public void moveProductToCart(Integer itemId) {
    log.info("moveProductToCart(itemId: {})", itemId);
    shoppingCartRepository.moveProductToCart(itemId);
  }

  public BigDecimal getTotalAmount(String cartId) {
    log.info("getTotalAmount(cartId: {})", cartId);
    return shoppingCartRepository.getTotalAmount(cartId);
  }

  @Transactional
  public void saveProductForLater(Integer itemId) {
    log.info("saveProductForLater(itemId: {})", itemId);
    shoppingCartRepository.saveProductForLater(itemId);
  }

  public List<CartProduct> getSavedProducts(String cartId) {
    log.info("getSavedProducts(cartId: {})", cartId);
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
    log.info("removeProduct(itemId: {})", itemId);
    shoppingCartRepository.removeProduct(itemId);
  }

  @Transactional
  @Scheduled(fixedRate = 1000 * 60 * 5)
  public void cleanup() {
    log.debug("cleanup old carts");
    try {
      shoppingCartRepository.cleanupOldCarts(cleanupInterval);
    } catch (Exception e) {
      log.error("", e);
    }
  }
}
