package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.Order;
import com.turing.backendapi.repository.entity.OrderEntity;

public class OrderConverter {

    public static OrderEntity toDb(Order domainObj) {
        if (domainObj == null) {
            return null;
        }
        return OrderEntity.builder()
            .order_id(domainObj.getOrder_id())
            .total_amount(domainObj.getTotal_amount())
            .created_on(domainObj.getCreated_on())
            .shipped_on(domainObj.getShipped_on())
            .status(domainObj.getStatus())
            .comments(domainObj.getComments())
            .customer_id(domainObj.getCustomer_id())
            .auth_code(domainObj.getAuth_code())
            .reference(domainObj.getReference())
            .shipping_id(domainObj.getShipping_id())
            .tax_id(domainObj.getTax_id())
            .build();

    }

    public static Order toDomain(OrderEntity entityObj) {
        if (entityObj == null) {
            return null;
        }
        return Order.builder()
            .order_id(entityObj.getOrder_id())
            .total_amount(entityObj.getTotal_amount())
            .created_on(entityObj.getCreated_on())
            .shipped_on(entityObj.getShipped_on())
            .status(entityObj.getStatus())
            .comments(entityObj.getComments())
            .customer_id(entityObj.getCustomer_id())
            .auth_code(entityObj.getAuth_code())
            .reference(entityObj.getReference())
            .shipping_id(entityObj.getShipping_id())
            .tax_id(entityObj.getTax_id())
            .build();

    }
}
