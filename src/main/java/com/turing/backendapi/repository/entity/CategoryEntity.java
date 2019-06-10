package com.turing.backendapi.repository.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {
  @Id
  @GeneratedValue
  @Column(name = "category_id")
  private int category_id;

  @Column(name = "department_id")
  private int department_id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;
}
