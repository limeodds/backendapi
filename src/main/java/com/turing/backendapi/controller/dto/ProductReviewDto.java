package com.turing.backendapi.controller.dto;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReviewDto {

    private String name;
    private String review;
    private int rating;
    private String created_on;
}
