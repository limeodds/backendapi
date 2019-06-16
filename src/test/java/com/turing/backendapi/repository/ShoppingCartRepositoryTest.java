package com.turing.backendapi.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartRepositoryTest {

  @Autowired
  private ShoppingCartRepository shoppingCartRepository;

  @Test
  @Transactional
  public void cleanupOldCarts() {
    shoppingCartRepository.cleanupOldCarts(5);
  }
}