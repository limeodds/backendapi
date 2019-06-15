package com.turing.backendapi.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "shipping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingEntity {
  @Id
  @GeneratedValue
  @Column(name = "shipping_id")
  private int shipping_id;

  @Column(name = "shipping_type")
  private String shipping_type;

  @Column(name = "shipping_cost")
  private BigDecimal shipping_cost;

  @Column(name = "shipping_region_id")
  private int shipping_region_id;
}
