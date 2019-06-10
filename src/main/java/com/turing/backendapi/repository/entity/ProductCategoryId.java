package com.turing.backendapi.repository.entity;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryId implements Serializable {
  @Column(name = "department_id")
  private int product_id;

  @Column(name = "category_id")
  private int category_id;
}
