package com.turing.backendapi.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeValue {
  private int attribute_value_id;
  private int attribute_id;
  private String value;
}
