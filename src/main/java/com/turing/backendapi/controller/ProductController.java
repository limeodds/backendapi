package com.turing.backendapi.controller;


import com.turing.backendapi.controller.converter.ProductDtoConverter;
import com.turing.backendapi.controller.dto.*;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.controller.exception.ErrorResponse;
import com.turing.backendapi.domain.DomainPage;
import com.turing.backendapi.domain.Product;
import com.turing.backendapi.service.ProductService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.turing.backendapi.controller.exception.ErrorCodes.GEN_01;
import static com.turing.backendapi.controller.exception.ErrorCodes.PRD_01;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Products", tags = {"products"})
public class ProductController implements Authentication, Validation {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  @ApiOperation(value = "Get All Products", notes = "Return a list of products.")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return the total of products and a list of Products in row.", response = DtoProductsPage.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "page", value = "Inform the page. Starting with 1. Default: 1", dataType = "int", paramType = "query"),
                     @ApiImplicitParam(name = "limit", value = "Limit per page, Default: 20.", dataType = "int", paramType = "query"),
                     @ApiImplicitParam(name = "description_length", value = "Limit of the description, Default: 200.", dataType = "int", paramType = "query")})
  public DtoProductsPage allProducts(@RequestParam(required = false, defaultValue = "1") Integer page,
                                     @RequestParam(required = false, defaultValue = "20") Integer limit,
                                     @RequestParam(required = false, defaultValue = "200") Integer description_length) {

    DomainPage<Product> domainPage = productService.getPage(page, limit);

    return new DtoProductsPage(domainPage.getCount(), domainPage.getRows().stream()
                                                                .map(o -> ProductDtoConverter.toDto(o, description_length)).collect(toList()));
  }

  @GetMapping("/search")
  @ApiOperation(value = "Search products")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return the total of products and a list of products.", response = DtoProductsPage.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "query_string", value = "Query to search.", required = true, dataType = "string", paramType = "query"),
                     @ApiImplicitParam(name = "all_words", value = "All words or no. Default: on. Available values : on, off", dataType = "string", paramType = "query"),
                     @ApiImplicitParam(name = "page", value = "Inform the page. Starting with 1. Default: 1", dataType = "int", paramType = "query"),
                     @ApiImplicitParam(name = "limit", value = "Limit per page, Default: 20.", dataType = "int", paramType = "query"),
                     @ApiImplicitParam(name = "description_length", value = "Limit of the description, Default: 200.", dataType = "int", paramType = "query")})
  public DtoProductsPage search(@RequestParam String query_string,
                                @RequestParam(required = false) String all_words,
                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                @RequestParam(required = false, defaultValue = "20") Integer limit,
                                @RequestParam(required = false, defaultValue = "200") Integer description_length) {
    checkNotEmpty(query_string, GEN_01, "query_string");

    query_string = Stream.of(query_string.split(" ")).map(s -> s + "*").collect(Collectors.joining(" "));

    all_words = "off".equals(all_words) ? "off" : "on";

    return new DtoProductsPage(productService.catalogSearchCount(query_string, all_words),
                               productService.catalogSearch(query_string, all_words, page - 1, limit, description_length)
                                             .stream()
                                             .map(o -> ProductDtoConverter.toDto(o, description_length)).collect(toList()));
  }

  @GetMapping("/{product_id}")
  @ApiOperation(value = "Product by ID")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a Product Object", response = ProductAllFieldsDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "product_id", value = "Product ID", required = true, dataType = "int", paramType = "path")})
  public ProductAllFieldsDto getById(@PathVariable Integer product_id) {
    checkNotEmpty(product_id, GEN_01, "product_id");

    Product byId = productService.getById(product_id);

    if (byId == null) {
      throw new BadRequestException(PRD_01.getCode(), PRD_01.getDescription(), "product_id");
    }

    return ProductDtoConverter.toDtoAllFields(byId);
  }

  @GetMapping("/inCategory/{category_id}")
  @ApiOperation(value = "Get a list of Products of Categories")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a list of Product Objects", response = DtoProductsPage.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "category_id", value = "Category ID", required = true, dataType = "string", paramType = "path"),
                     @ApiImplicitParam(name = "page", value = "Inform the page. Starting with 1. Default: 1", dataType = "int", paramType = "query"),
                     @ApiImplicitParam(name = "limit", value = "Limit per page", dataType = "int", paramType = "query"),
                     @ApiImplicitParam(name = "description_length", value = "Limit of the description", dataType = "int", paramType = "query")})

  public DtoProductsPage getByCategoryId(@PathVariable Integer category_id,
                                         @RequestParam(required = false, defaultValue = "1") Integer page,
                                         @RequestParam(required = false, defaultValue = "999999") Integer limit,
                                         @RequestParam(required = false, defaultValue = "999999") Integer description_length) {
    checkNotEmpty(category_id, GEN_01, "category_id");

    return new DtoProductsPage(productService.productsInCategoryCount(category_id),
                               productService.productsInCategory(category_id, page, limit, description_length)
                                             .stream()
                                             .map(o -> ProductDtoConverter.toDto(o, description_length)).collect(toList()));
  }

  @GetMapping("/inDepartment/{department_id}")
  @ApiOperation(value = "Get a list of Products on Department")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return the total and a list of products", response = DtoProductsPage.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "department_id", value = "Department ID", required = true, dataType = "string", paramType = "path"),
                     @ApiImplicitParam(name = "page", value = "Inform the page. Starting with 1. Default: 1", dataType = "int", paramType = "query"),
                     @ApiImplicitParam(name = "limit", value = "Limit per page", dataType = "int", paramType = "query"),
                     @ApiImplicitParam(name = "description_length", value = "Limit of the description", dataType = "int", paramType = "query")})
  public DtoProductsPage getByDepartmentId(@PathVariable Integer department_id,
                                           @RequestParam(required = false, defaultValue = "1") Integer page,
                                           @RequestParam(required = false, defaultValue = "999999") Integer limit,
                                           @RequestParam(required = false, defaultValue = "999999") Integer description_length) {
    checkNotEmpty(department_id, GEN_01, "department_id");

    return new DtoProductsPage(productService.productsInDepartmentCount(department_id),
                               productService.productsInDepartment(department_id, page, limit, description_length)
                                             .stream()
                                             .map(o -> ProductDtoConverter.toDto(o, description_length)).collect(toList()));
  }

  @GetMapping("/{product_id}/details")
  @ApiOperation(value = "Get details of a Product")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a Object of Products", response = ProductDetailsDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "product_id", value = "Product ID", required = true, dataType = "string", paramType = "path")})
  public ProductDetailsDto getDetails(@PathVariable Integer product_id) {
    checkNotEmpty(product_id, GEN_01, "product_id");

    Product byId = productService.getById(product_id);

    if (byId == null) {
      throw new BadRequestException(PRD_01.getCode(), PRD_01.getDescription(), "product_id");
    }

    return ProductDetailsDto.builder()
                            .product_id(byId.getProduct_id())
                            .name(byId.getName())
                            .description(byId.getDescription())
                            .price(DtoUtils.format(byId.getPrice()))
                            .discounted_price(DtoUtils.format(byId.getDiscounted_price()))
                            .image(byId.getImage())
                            .image_2(byId.getImage_2())
                            .build();
  }

  @GetMapping("/{product_id}/locations")
  @ApiOperation(value = "Get locations of a Product")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return locations of products.", response = ProductLocationDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "product_id", value = "Product ID", required = true, dataType = "string", paramType = "path")})
  public List<ProductLocationDto> getLocations(@PathVariable Integer product_id) {
    checkNotEmpty(product_id, GEN_01, "product_id");

    return productService.getProductLocations(product_id).stream()
                         .map(o -> ProductLocationDto.builder()
                                                     .category_id(o.getCategory_id())
                                                     .category_name(o.getCategory_name())
                                                     .department_id(o.getDepartment_id())
                                                     .department_name(o.getDepartment_name())
                                                     .build())
                         .collect(toList());
  }

  @GetMapping("/{product_id}/reviews")
  @ApiOperation(value = "Get reviews of a Product")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a list of reviews", response = ProductReviewDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "product_id", value = "Product ID", required = true, dataType = "string", paramType = "path")})
  public ProductReviewDto[] getReviews(@PathVariable Integer product_id) {
    checkNotEmpty(product_id, GEN_01, "product_id");

    List<ProductReviewDto> reviews = productService.getProductReviews(product_id).stream()
                                                   .map(o -> ProductReviewDto.builder()
                                                                             .name(o.getName())
                                                                             .review(o.getReview())
                                                                             .rating(o.getRating())
                                                                             .created_on(DtoUtils.format(o.getCreated_on()))
                                                                             .build())
                                                   .collect(toList());
    return reviews.toArray(new ProductReviewDto[reviews.size()]);
  }

  @PostMapping(path = "/{product_id}/reviews", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ApiOperation(value = "Create a review")
  @ApiResponses({
                @ApiResponse(code = 200, message = "No data."),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "USER-KEY", value = "JWT Token", dataType = "string", paramType = "header"),
                     @ApiImplicitParam(name = "product_id", value = "Product ID", required = true, dataType = "string", paramType = "path"),
                     @ApiImplicitParam(name = "review", value = "Review Text of Product", required = true, dataType = "string", paramType = "form"),
                     @ApiImplicitParam(name = "rating", value = "Rating of Product", required = true, dataType = "int", paramType = "form")
                     })
  public void createReview(@PathVariable Integer product_id,
                           @RequestParam String review,
                           @RequestParam Integer rating) {
    checkNotEmpty(product_id, GEN_01, "product_id");
    checkNotEmpty(review, GEN_01, "review");
    checkNotEmpty(rating, GEN_01, "rating");

    productService.createProductReview(getPrincipal().getId(), product_id, review, rating);
  }

}
