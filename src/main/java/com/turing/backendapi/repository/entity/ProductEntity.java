package com.turing.backendapi.repository.entity;


import java.math.BigDecimal;
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
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {
  @Id
  @GeneratedValue
  @Column(name = "product_id")
  private int product_id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "discounted_price")
  private BigDecimal discounted_price;

  @Column(name = "image")
  private String image;

  @Column(name = "image_2")
  private String image_2;

  @Column(name = "thumbnail")
  private String thumbnail;

  @Column(name = "display")
  private int display;
}
