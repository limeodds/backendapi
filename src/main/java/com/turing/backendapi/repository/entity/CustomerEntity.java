package com.turing.backendapi.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity {

  @Id
  @GeneratedValue
  @Column(name = "customer_id")
  private int customer_id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "credit_card")
  private String credit_card;

  @Column(name = "address_1")
  private String address_1;

  @Column(name = "address_2")
  private String address_2;

  @Column(name = "city")
  private String city;

  @Column(name = "region")
  private String region;

  @Column(name = "postal_code")
  private String postal_code;

  @Column(name = "country")
  private String country;

  @Column(name = "shipping_region_id")
  private int shipping_region_id;

  @Column(name = "day_phone")
  private String day_phone;

  @Column(name = "eve_phone")
  private String eve_phone;

  @Column(name = "mob_phone")
  private String mob_phone;
}
