package com.turing.backendapi.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
  private int customer_id;
  private String name;
  private String email;
  private String passwordHash;
  private String salt;
  private String credit_card;
  private String address_1;
  private String address_2;
  private String city;
  private String region;
  private String postal_code;
  private String country;
  private int shipping_region_id;
  private String day_phone;
  private String eve_phone;
  private String mob_phone;
}
