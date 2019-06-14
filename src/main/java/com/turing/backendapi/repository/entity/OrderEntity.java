package com.turing.backendapi.repository.entity;


import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
  @Id
  @GeneratedValue
  @Column(name = "order_id")
  private int order_id;

  @Column(name = "total_amount")
  private BigDecimal total_amount;

  @Column(name = "created_on")
  private LocalDateTime created_on;

  @Column(name = "shipped_on")
  private LocalDateTime shipped_on;

  @Column(name = "status")
  private int status;

  @Column(name = "comments")
  private String comments;

  @Column(name = "customer_id")
  private int customer_id;

  @Column(name = "auth_code")
  private String auth_code;

  @Column(name = "reference")
  private String reference;

  @Column(name = "shipping_id")
  private int shipping_id;

  @Column(name = "tax_id")
  private int tax_id;
}
