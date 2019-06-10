package com.turing.backendapi.domain;


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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
  private int product_id;
  private String name;
  private String description;
  private BigDecimal price;
  private BigDecimal discounted_price;
  private String image;
  private String image_2;
  private String thumbnail;
  private int display;
}
