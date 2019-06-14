package com.turing.backendapi.domain;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
  private int order_id;
  private BigDecimal total_amount;
  private LocalDateTime created_on;
  private LocalDateTime shipped_on;
  private int status;
  private String comments;
  private int customer_id;
  private String auth_code;
  private String reference;
  private int shipping_id;
  private int tax_id;
}
