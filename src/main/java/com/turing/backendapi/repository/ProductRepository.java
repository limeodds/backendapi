package com.turing.backendapi.repository;

import com.turing.backendapi.repository.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Integer> {
  Page<ProductEntity> findAll(Pageable pageable);

  @Query(nativeQuery = true, value = "call catalog_count_search_result(:inSearchString, :inAllWords)")
  Integer catalogSearchCount(@Param("inSearchString") String inSearchString, @Param("inAllWords") String inAllWords);


  @Query(nativeQuery = true, value = "call catalog_search(:inSearchString, :inAllWords, :inShortProductDescriptionLength, :inProductsPerPage, :inStartItem)")
  List<Object[]> catalogSearch(@Param("inSearchString") String inSearchString,
                               @Param("inAllWords") String inAllWords,
                               @Param("inShortProductDescriptionLength") int inShortProductDescriptionLength,
                               @Param("inProductsPerPage") int inProductsPerPage,
                               @Param("inStartItem") int inStartItem);

  @Query(nativeQuery = true, value = "call catalog_count_products_in_category(:inCategoryId)")
  Integer productsInCategoryCount(@Param("inCategoryId") int inCategoryId);

  @Query(nativeQuery = true, value = "call catalog_get_products_in_category(:inCategoryId, :inShortProductDescriptionLength, :inProductsPerPage, :inStartItem)")
  List<Object[]> productsInCategory(@Param("inCategoryId") int inCategoryId,
                                    @Param("inShortProductDescriptionLength") int inShortProductDescriptionLength,
                                    @Param("inProductsPerPage") int inProductsPerPage,
                                    @Param("inStartItem") int inStartItem);


  @Query(nativeQuery = true, value = "call catalog_count_products_on_department(:inDepartmentId)")
  Integer productsInDepartmentCount(@Param("inDepartmentId") int inDepartmentId);

  @Query(nativeQuery = true, value = "call catalog_get_products_on_department(:inDepartmentId, :inShortProductDescriptionLength, :inProductsPerPage, :inStartItem)")
  List<Object[]> productsInDepartment(@Param("inDepartmentId") int inDepartmentId,
                                      @Param("inShortProductDescriptionLength") int inShortProductDescriptionLength,
                                      @Param("inProductsPerPage") int inProductsPerPage,
                                      @Param("inStartItem") int inStartItem);

  @Query(nativeQuery = true, value = "call catalog_get_product_locations(:inProductId)")
  List<Object[]> productLocations(@Param("inProductId") int inProductId);

  @Query(nativeQuery = true, value = "call catalog_get_product_reviews(:inProductId)")
  List<Object[]> productReviews(@Param("inProductId") int inProductId);

  @Modifying
  @Query(nativeQuery = true, value = "call catalog_create_product_review(:inCustomerId, :inProductId, :inReview, :inRating)")
  void createProductReview(@Param("inCustomerId") int inCustomerId,
                           @Param("inProductId") int inProductId,
                           @Param("inReview") String inReview,
                           @Param("inRating") int inRating);
}
