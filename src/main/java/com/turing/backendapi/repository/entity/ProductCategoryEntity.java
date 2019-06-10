package com.turing.backendapi.repository.entity;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryEntity {
    @EmbeddedId
    private ProductCategoryId id;

    public int getProduct_id() {
        return id.getProduct_id();
    }

    public int getCategory_id() {
        return id.getCategory_id();
    }
}
