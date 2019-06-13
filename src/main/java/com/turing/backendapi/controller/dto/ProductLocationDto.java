package com.turing.backendapi.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductLocationDto {

    private int category_id;
    private String category_name;
    private int department_id;
    private String department_name;
}
