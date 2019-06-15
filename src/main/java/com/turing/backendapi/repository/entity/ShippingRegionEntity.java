package com.turing.backendapi.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "shipping_region")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingRegionEntity {
  @Id
  @GeneratedValue
  @Column(name = "shipping_region_id")
  private int shipping_region_id;

  @Column(name = "shipping_region")
  private String shipping_region;
}
