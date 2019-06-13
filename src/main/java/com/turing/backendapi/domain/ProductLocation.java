package com.turing.backendapi.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductLocation {

    private int category_id;
    private String category_name;
    private int department_id;
    private String department_name;
}
