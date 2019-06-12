package com.turing.backendapi.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {
  @Autowired
  private ProductRepository productRepository;

  @Test
  public void catalogSearchCount() {
    System.out.println(productRepository.catalogSearchCount("Coat", "on"));
    System.out.println(productRepository.catalogSearchCount("Coat Italy", "off"));
  }

  @Test
  public void catalogSearch() {
    List<Object[]> objects = productRepository.catalogSearch("Coat Italy", "off", 50, 3, 1);
  }
}