package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.Customer;
import com.turing.backendapi.repository.entity.CustomerEntity;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerConverterTest {

  @Test
  public void toDb() {
    //given
    Customer domainObj = new Customer(123,
                                      "name",
                                      "email",
                                      "password",
                                      "credit_card",
                                      "address_1",
                                      "address_2",
                                      "city",
                                      "region",
                                      "postal_code",
                                      "country",
                                      456,
                                      "day_phone",
                                      "eve_phone",
                                      "mob_phone");

    //when
    CustomerEntity entityObj = CustomerConverter.toDb(domainObj);

    //then
    assertThat(entityObj.getCustomer_id()).isEqualTo(domainObj.getCustomer_id());
    assertThat(entityObj.getName()).isEqualTo(domainObj.getName());
    assertThat(entityObj.getEmail()).isEqualTo(domainObj.getEmail());
    assertThat(entityObj.getPassword()).isEqualTo(domainObj.getPassword());
    assertThat(entityObj.getCredit_card()).isEqualTo(domainObj.getCredit_card());
    assertThat(entityObj.getAddress_1()).isEqualTo(domainObj.getAddress_1());
    assertThat(entityObj.getAddress_2()).isEqualTo(domainObj.getAddress_2());
    assertThat(entityObj.getCity()).isEqualTo(domainObj.getCity());
    assertThat(entityObj.getRegion()).isEqualTo(domainObj.getRegion());
    assertThat(entityObj.getPostal_code()).isEqualTo(domainObj.getPostal_code());
    assertThat(entityObj.getCountry()).isEqualTo(domainObj.getCountry());
    assertThat(entityObj.getShipping_region_id()).isEqualTo(domainObj.getShipping_region_id());
    assertThat(entityObj.getDay_phone()).isEqualTo(domainObj.getDay_phone());
    assertThat(entityObj.getEve_phone()).isEqualTo(domainObj.getEve_phone());
    assertThat(entityObj.getMob_phone()).isEqualTo(domainObj.getMob_phone());
  }

  @Test
  public void toDomain() {
    //given
    CustomerEntity entityObj = new CustomerEntity(123,
                                                  "name",
                                                  "email",
                                                  "password",
                                                  "credit_card",
                                                  "address_1",
                                                  "address_2",
                                                  "city",
                                                  "region",
                                                  "postal_code",
                                                  "country",
                                                  456,
                                                  "day_phone",
                                                  "eve_phone",
                                                  "mob_phone");

    //when
    Customer domainObj = CustomerConverter.toDomain(entityObj);

    //then
    assertThat(domainObj.getCustomer_id()).isEqualTo(entityObj.getCustomer_id());
    assertThat(domainObj.getName()).isEqualTo(entityObj.getName());
    assertThat(domainObj.getEmail()).isEqualTo(entityObj.getEmail());
    assertThat(domainObj.getPassword()).isEqualTo(entityObj.getPassword());
    assertThat(domainObj.getCredit_card()).isEqualTo(entityObj.getCredit_card());
    assertThat(domainObj.getAddress_1()).isEqualTo(entityObj.getAddress_1());
    assertThat(domainObj.getAddress_2()).isEqualTo(entityObj.getAddress_2());
    assertThat(domainObj.getCity()).isEqualTo(entityObj.getCity());
    assertThat(domainObj.getRegion()).isEqualTo(entityObj.getRegion());
    assertThat(domainObj.getPostal_code()).isEqualTo(entityObj.getPostal_code());
    assertThat(domainObj.getCountry()).isEqualTo(entityObj.getCountry());
    assertThat(domainObj.getShipping_region_id()).isEqualTo(entityObj.getShipping_region_id());
    assertThat(domainObj.getDay_phone()).isEqualTo(entityObj.getDay_phone());
    assertThat(domainObj.getEve_phone()).isEqualTo(entityObj.getEve_phone());
    assertThat(domainObj.getMob_phone()).isEqualTo(entityObj.getMob_phone());
  }
}