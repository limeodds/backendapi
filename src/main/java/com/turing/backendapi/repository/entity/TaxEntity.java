package com.turing.backendapi.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tax")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxEntity {
  @Id
  @GeneratedValue
  @Column(name = "tax_id")
  private int tax_id;

  @Column(name = "tax_type")
  private String tax_type;

  @Column(name = "tax_percentage")
  private BigDecimal tax_percentage;
}
