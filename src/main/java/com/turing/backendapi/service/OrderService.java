package com.turing.backendapi.service;

import static com.turing.backendapi.controller.exception.ErrorCodes.PAG_03;
import static com.turing.backendapi.service.converter.ProductConverter.toDomain;
import static java.util.stream.Collectors.toList;

import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.domain.DomainPage;
import com.turing.backendapi.domain.Product;
import com.turing.backendapi.domain.ProductLocation;
import com.turing.backendapi.domain.ProductReview;
import com.turing.backendapi.repository.OrderRepository;
import com.turing.backendapi.repository.ProductRepository;
import com.turing.backendapi.repository.entity.ProductEntity;
import com.turing.backendapi.service.converter.ProductConverter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
  public int createOrder(String cartId, int customerId, int shippingId, int taxId){
    return orderRepository.createOrder(cartId, customerId, shippingId, taxId);
  }

}
