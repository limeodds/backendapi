package com.turing.backendapi.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "attribute")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeEntity {
  @Id
  @GeneratedValue
  @Column(name = "attribute_id")
  private int attribute_id;

  @Column(name = "name")
  private String name;
}
