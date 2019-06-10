package com.turing.backendapi.repository;

import com.turing.backendapi.repository.entity.CustomerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {
  @Autowired
  private CustomerRepository customerRepository;

//  @Test
  public void create() {
    CustomerEntity entiry = CustomerEntity.builder()
                                          .name("Liviu Marinescu")
                                          .email("liviu.marinescu@yahoo.com")
                                          .password("turingrocks")
                                          .credit_card("1234567890")
                                          .address_1("Address 1")
                                          .address_2("Address 2")
                                          .city("Barcelona")
                                          .region("Andalusia")
                                          .postal_code("75654")
                                          .country("Spain")
                                          .shipping_region_id(123)
                                          .day_phone("123455-222")
                                          .eve_phone("1234555-444")
                                          .mob_phone("32423545345")
                                          .build();

    customerRepository.save(entiry);

  }

}