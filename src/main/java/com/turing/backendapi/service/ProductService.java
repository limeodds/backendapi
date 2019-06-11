package com.turing.backendapi.service;

import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.domain.DomainPage;
import com.turing.backendapi.domain.Product;
import com.turing.backendapi.repository.ProductRepository;
import com.turing.backendapi.repository.entity.ProductEntity;
import com.turing.backendapi.service.converter.ProductConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.turing.backendapi.controller.exception.ErrorCodes.PAG_03;
import static com.turing.backendapi.service.converter.ProductConverter.toDomain;
import static java.util.stream.Collectors.toList;


@Service
@Transactional(readOnly = true)
@Slf4j
public class ProductService {

  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  /**
   * @param page Starting with 1. Default: 1. Be aware that Hibernate use a 0 based page index.
   * @param limit Items per page
   */
  public DomainPage<Product> getPage(int page, int limit) {
    log.debug("getPage(page:{}, limit: {}", page, limit);
    if (page < 1) {
      throw new BadRequestException(PAG_03.getCode(), PAG_03.getDescription(), "page");
    }

    if (limit < 1) {
      throw new BadRequestException(PAG_03.getCode(), PAG_03.getDescription(), "limit");
    }

    Pageable pageable = PageRequest.of(page - 1, limit);

    Page<ProductEntity> pageEntity = productRepository.findAll(pageable);

    return new DomainPage<>(pageEntity.getTotalElements(), pageEntity.get().map(ProductConverter::toDomain).collect(toList()));
  }

  public Product getById(Integer id) {
    return toDomain(productRepository.findById(id).orElse(null));
  }


}
