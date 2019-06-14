package com.turing.backendapi.service.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.turing.backendapi.domain.Order;
import com.turing.backendapi.repository.entity.OrderEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.Test;

public class OrderConverterTest {

    @Test
    public void toDb() {
        //given
        Order domainObj = new Order(1, BigDecimal.valueOf(1.2), LocalDateTime.now(), LocalDateTime.now().plusHours(5), 1, "comments", 23, "auth_code bla",
            "refe xyz", 45, 67);

        //when
        OrderEntity entityObj = OrderConverter.toDb(domainObj);

        //then
        assertThat(entityObj.getOrder_id()).isEqualTo(domainObj.getOrder_id());
        assertThat(entityObj.getTotal_amount()).isEqualTo(domainObj.getTotal_amount());
        assertThat(entityObj.getCreated_on()).isEqualTo(domainObj.getCreated_on());
        assertThat(entityObj.getShipped_on()).isEqualTo(domainObj.getShipped_on());
        assertThat(entityObj.getStatus()).isEqualTo(domainObj.getStatus());
        assertThat(entityObj.getComments()).isEqualTo(domainObj.getComments());
        assertThat(entityObj.getCustomer_id()).isEqualTo(domainObj.getCustomer_id());
        assertThat(entityObj.getAuth_code()).isEqualTo(domainObj.getAuth_code());
        assertThat(entityObj.getReference()).isEqualTo(domainObj.getReference());
        assertThat(entityObj.getShipping_id()).isEqualTo(domainObj.getShipping_id());
        assertThat(entityObj.getTax_id()).isEqualTo(domainObj.getTax_id());

    }

    @Test
    public void toDomain() {
        //given
        OrderEntity entityObj = new OrderEntity(1, BigDecimal.valueOf(2.4), LocalDateTime.now(), LocalDateTime.now().plusHours(5), 1, "comments ccc", 23, "auth_code blas",
            "refe xyzd", 45, 67);

        //when
        Order domainObj = OrderConverter.toDomain(entityObj);

        //then
        assertThat(domainObj.getOrder_id()).isEqualTo(entityObj.getOrder_id());
        assertThat(domainObj.getOrder_id()).isEqualTo(entityObj.getOrder_id());
        assertThat(domainObj.getTotal_amount()).isEqualTo(entityObj.getTotal_amount());
        assertThat(domainObj.getCreated_on()).isEqualTo(entityObj.getCreated_on());
        assertThat(domainObj.getShipped_on()).isEqualTo(entityObj.getShipped_on());
        assertThat(domainObj.getStatus()).isEqualTo(entityObj.getStatus());
        assertThat(domainObj.getComments()).isEqualTo(entityObj.getComments());
        assertThat(domainObj.getCustomer_id()).isEqualTo(entityObj.getCustomer_id());
        assertThat(domainObj.getAuth_code()).isEqualTo(entityObj.getAuth_code());
        assertThat(domainObj.getReference()).isEqualTo(entityObj.getReference());
        assertThat(domainObj.getShipping_id()).isEqualTo(entityObj.getShipping_id());
        assertThat(domainObj.getTax_id()).isEqualTo(entityObj.getTax_id());
    }
}