package com.turing.backendapi.service;

import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.domain.DomainPage;
import com.turing.backendapi.domain.Product;
import com.turing.backendapi.domain.ProductLocation;
import com.turing.backendapi.domain.ProductReview;
import com.turing.backendapi.repository.ProductRepository;
import com.turing.backendapi.repository.entity.ProductEntity;
import com.turing.backendapi.service.converter.ProductConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

  public int catalogSearchCount(String searchString, String allWords){
    return productRepository.catalogSearchCount(searchString, allWords);
  }

  public List<Product> catalogSearch(String searchString, String allWords, int page, int limit, int description_length) {
    return productRepository.catalogSearch(searchString, allWords, description_length, limit, page)
                            .stream()
                            .map(o -> Product.builder()
                                             .product_id((Integer) o[0])
                                             .name((String) o[1])
                                             .description((String) o[2])
                                             .price((BigDecimal) o[3])
                                             .discounted_price((BigDecimal) o[4])
                                             .thumbnail((String) o[5])
                                             .build())
                            .collect(toList());
  }

  public int productsInCategoryCount(int categoryId){
    return productRepository.productsInCategoryCount(categoryId);
  }

  public List<Product> productsInCategory(int categoryId, int page, int limit, int description_length) {
    return productRepository.productsInCategory(categoryId, description_length, limit, page)
                            .stream()
                            .map(o -> Product.builder()
                                             .product_id((Integer) o[0])
                                             .name((String) o[1])
                                             .description((String) o[2])
                                             .price((BigDecimal) o[3])
                                             .discounted_price((BigDecimal) o[4])
                                             .thumbnail((String) o[5])
                                             .build())
                            .collect(toList());
  }


  public int productsInDepartmentCount(int departmentId){
    return productRepository.productsInDepartmentCount(departmentId);
  }

  public List<Product> productsInDepartment(int departmentId, int page, int limit, int description_length) {
    return productRepository.productsInDepartment(departmentId, description_length, limit, page)
                            .stream()
                            .map(o -> Product.builder()
                                             .product_id((Integer) o[0])
                                             .name((String) o[1])
                                             .description((String) o[2])
                                             .price((BigDecimal) o[3])
                                             .discounted_price((BigDecimal) o[4])
                                             .thumbnail((String) o[5])
                                             .build())
                            .collect(toList());
  }

  public Product getById(Integer id) {
    return toDomain(productRepository.findById(id).orElse(null));
  }

  public List<ProductLocation> getProductLocations(int productId) {
    return productRepository.productLocations(productId)
        .stream()
        .map(o -> ProductLocation.builder()
            .category_id((Integer) o[0])
            .category_name((String) o[1])
            .department_id((Integer) o[2])
            .department_name((String) o[3])
            .build())
        .collect(toList());
  }

  public List<ProductReview> getProductReviews(int productId) {
    return productRepository.productReviews(productId)
        .stream()
        .map(o -> ProductReview.builder()
            .name((String) o[0])
            .review((String) o[1])
            .rating((Short) o[2])
            .created_on(((Timestamp) o[3]).toLocalDateTime())
            .build())
        .collect(toList());
  }

  @Transactional
  public void createProductReview(int customerId, int productId, String review, int rating){
    productRepository.createProductReview(customerId, productId, review, rating);
  }
}
