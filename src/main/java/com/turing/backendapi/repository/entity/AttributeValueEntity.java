package com.turing.backendapi.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "attribute_value")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeValueEntity {
  @Id
  @GeneratedValue
  @Column(name = "attribute_value_id")
  private int attribute_value_id;

  @Column(name = "attribute_id")
  private int attribute_id;

  @Column(name = "value")
  private String value;
}
