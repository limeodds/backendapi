package com.turing.backendapi.service;

import com.turing.backendapi.repository.ShoppingCartRepository;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
}
