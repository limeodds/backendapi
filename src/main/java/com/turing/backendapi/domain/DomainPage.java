package com.turing.backendapi.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor @Builder
public class DomainPage<T> {
    private long count;
    private List<T> rows;
}
