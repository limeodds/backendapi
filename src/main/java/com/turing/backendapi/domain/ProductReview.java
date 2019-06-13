package com.turing.backendapi.domain;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReview {

    private String name;
    private String review;
    private int rating;
    private LocalDateTime created_on;
}
