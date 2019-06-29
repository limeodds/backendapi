package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.Customer;
import com.turing.backendapi.repository.entity.CustomerEntity;

public class CustomerConverter {
  public static CustomerEntity toDb(Customer domainObj) {
    if (domainObj == null) {
      return null;
    }
    return CustomerEntity.builder()
                         .customer_id(domainObj.getCustomer_id())
                         .name(domainObj.getName())
                         .email(domainObj.getEmail())
                         .passwordHash(domainObj.getPasswordHash())
                         .salt(domainObj.getSalt())
                         .credit_card(domainObj.getCredit_card())
                         .address_1(domainObj.getAddress_1())
                         .address_2(domainObj.getAddress_2())
                         .city(domainObj.getCity())
                         .region(domainObj.getRegion())
                         .postal_code(domainObj.getPostal_code())
                         .country(domainObj.getCountry())
                         .shipping_region_id(domainObj.getShipping_region_id())
                         .day_phone(domainObj.getDay_phone())
                         .eve_phone(domainObj.getEve_phone())
                         .mob_phone(domainObj.getMob_phone())
                         .build();

  }

  public static Customer toDomain(CustomerEntity entityObj) {
    if (entityObj == null) {
      return null;
    }
    return Customer.builder()
                   .customer_id(entityObj.getCustomer_id())
                   .name(entityObj.getName())
                   .email(entityObj.getEmail())
                   .passwordHash(entityObj.getPasswordHash())
                   .salt(entityObj.getSalt())
                   .credit_card(entityObj.getCredit_card())
                   .address_1(entityObj.getAddress_1())
                   .address_2(entityObj.getAddress_2())
                   .city(entityObj.getCity())
                   .region(entityObj.getRegion())
                   .postal_code(entityObj.getPostal_code())
                   .country(entityObj.getCountry())
                   .shipping_region_id(entityObj.getShipping_region_id())
                   .day_phone(entityObj.getDay_phone())
                   .eve_phone(entityObj.getEve_phone())
                   .mob_phone(entityObj.getMob_phone())
                   .build();

  }
}
