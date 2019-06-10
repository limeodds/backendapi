package com.turing.backendapi.repository.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentEntity {

  @Id
  @GeneratedValue
  @Column(name = "department_id")
  private int department_id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;
}
