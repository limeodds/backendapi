package com.turing.backendapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderShortDetails {
  private int order_id;
  private BigDecimal total_amount;
  private LocalDateTime created_on;
  private LocalDateTime shipped_on;
  private int status;
  private String name;
}
