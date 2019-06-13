package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.CustomerDto;
import com.turing.backendapi.domain.Customer;

public class CustomerDtoConverter {

  public static CustomerDto toDto(Customer domainObj) {
    if (domainObj == null) {
      return null;
    }


    return CustomerDto.builder()
                      .customer_id(domainObj.getCustomer_id())
                      .name(domainObj.getName())
                      .email(domainObj.getEmail())
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
                      .credit_card(domainObj.getCredit_card())
                      .build();
  }

}
