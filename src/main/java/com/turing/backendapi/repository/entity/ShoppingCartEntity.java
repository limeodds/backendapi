package com.turing.backendapi.repository.entity;


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

@Entity
@Table(name = "shopping_cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartEntity {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private int item_id;

    @Column(name = "cart_id")
    private String cart_id;

    @Column(name = "product_id")
    private int product_id;

    @Column(name = "attributes")
    private String attributes;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "buy_now")
    private boolean buy_now;

    @Column(name = "added_on")
    private LocalDateTime added_on;
}
