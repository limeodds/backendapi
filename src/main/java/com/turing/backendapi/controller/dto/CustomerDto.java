package com.turing.backendapi.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
  private int customer_id;
  private String name;
  private String email;
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
  private String credit_card;
}
