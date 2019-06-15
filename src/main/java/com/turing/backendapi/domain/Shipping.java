package com.turing.backendapi.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipping {
  private int shipping_id;
  private String shipping_type;
  private BigDecimal shipping_cost;
  private int shipping_region_id;
}
