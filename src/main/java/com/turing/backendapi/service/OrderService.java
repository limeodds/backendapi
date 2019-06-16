package com.turing.backendapi.service;

import com.turing.backendapi.domain.OrderDetails;
import com.turing.backendapi.domain.OrderShortDetails;
import com.turing.backendapi.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@Transactional(readOnly = true)
@Slf4j
public class OrderService {

  private final OrderRepository orderRepository;

  @Autowired
  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Transactional
  public int createOrder(String cartId, int customerId, int shippingId, int taxId) {
    return orderRepository.createOrder(cartId, customerId, shippingId, taxId);
  }

  public OrderDetails getOrderDetails(int orderId) {
    List<Object[]> os = orderRepository.getOrderDetails(orderId);

    if(CollectionUtils.isEmpty(os)){
      return null;
    }

    Object[] o = os.get(0);

    return OrderDetails.builder()
                       .order_id((Integer) o[0])
                       .product_id((Integer) o[1])
                       .attributes((String) o[2])
                       .product_name((String) o[3])
                       .quantity((Integer) o[4])
                       .unit_cost((BigDecimal) o[5])
                       .subtotal((BigDecimal) o[6])
                       .build();
  }

  public List<OrderShortDetails> getOrdersByCustomer(int customerId) {
    return orderRepository.getOrdersByCustomer(customerId).stream()
                          .map(o -> OrderShortDetails.builder()
                                                     .order_id((Integer) o[0])
                                                     .total_amount((BigDecimal) o[1])
                                                     .created_on(((Timestamp) o[2]).toLocalDateTime())
                                                     .shipped_on(o[3] != null ? ((Timestamp) o[3]).toLocalDateTime() : null)
                                                     .status((Integer) o[4])
                                                     .name((String) o[5])
                                                     .build()).collect(toList());
  }

  public OrderShortDetails getOrderShortDetails(int orderId) {
    List<Object[]> os = orderRepository.getOrderShortDetails(orderId);

    if(CollectionUtils.isEmpty(os)){
      return null;
    }

    Object[] o = os.get(0);

    return OrderShortDetails.builder()
                            .order_id((Integer) o[0])
                            .total_amount((BigDecimal) o[1])
                            .created_on(((Timestamp) o[2]).toLocalDateTime())
                            .shipped_on(o[3] != null ? ((Timestamp) o[3]).toLocalDateTime() : null)
                            .status((Integer) o[4])
                            .name((String) o[5])
                            .build();
  }
}
