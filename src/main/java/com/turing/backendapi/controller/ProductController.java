package com.turing.backendapi.controller;


import com.turing.backendapi.controller.converter.ProductDtoConverter;
import com.turing.backendapi.controller.dto.DtoPage;
import com.turing.backendapi.controller.dto.ProductAllFieldsDto;
import com.turing.backendapi.controller.dto.ProductDetailsDto;
import com.turing.backendapi.controller.dto.ProductDto;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.domain.DomainPage;
import com.turing.backendapi.domain.Product;
import com.turing.backendapi.service.ProductService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.turing.backendapi.controller.exception.ErrorCodes.GEN_01;
import static com.turing.backendapi.controller.exception.ErrorCodes.PRD_01;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Products", tags = {"products"})
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  @ApiOperation(value = "Get All Products")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return the total of products and a list of Products in row."),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public DtoPage<ProductDto> allProducts(
  @ApiParam(value = "Inform the page. Starting with 1. Default: 1") @RequestParam(required = false) Integer page,
  @ApiParam(value = "Limit per page, Default: 20.") @RequestParam(required = false) Integer limit,
  @ApiParam(value = "Limit of the description, Default: 200.") @RequestParam(required = false) Integer description_length) {
    page = page != null ? page : 1;
    limit = limit != null ? limit : 20;

    int descriptionLength = description_length == null ? 200 : description_length;

    DomainPage<Product> domainPage = productService.getPage(page, limit);

    return new DtoPage<>(domainPage.getCount(), domainPage.getRows().stream()
                                                          .map(o -> ProductDtoConverter.toDto(o, descriptionLength)).collect(toList()));
  }

  @GetMapping("/search")
  @ApiOperation(value = "Search products")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return the total of products and a list of products."),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public DtoPage<ProductDto> search(
  @ApiParam(value = "Query to search.", required = true) @RequestParam String query_string,
  @ApiParam(value = "All words or no. Default: on. Available values : on, off") @RequestParam(required = false) String all_words,
  @ApiParam(value = "Inform the page. Starting with 1. Default: 1") @RequestParam(required = false) Integer page,
  @ApiParam(value = "Limit per page, Default: 20.") @RequestParam(required = false) Integer limit,
  @ApiParam(value = "Limit of the description, Default: 200.") @RequestParam(required = false) Integer description_length) {
    if (StringUtils.isEmpty(query_string)) {
      throw new BadRequestException(GEN_01.getCode(), GEN_01.getDescription(), "query_string");
    }

    page = page != null ? page : 1;
    limit = limit != null ? limit : 20;
    all_words = "off".equals(all_words) ? "off" : "on";
    int descriptionLength = description_length == null ? 200 : description_length;

    return new DtoPage<>(productService.catalogSearchCount(query_string, all_words),
                         productService.catalogSearch(query_string, all_words, page, limit, descriptionLength)
                                       .stream()
                                       .map(o -> ProductDtoConverter.toDto(o, descriptionLength)).collect(toList()));
  }

  @GetMapping("/{product_id}")
  @ApiOperation(value = "Product by ID")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return a Product Object"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public ProductAllFieldsDto getById(@ApiParam(value = "Product ID", required = true) @RequestParam Integer product_id) {
    if (StringUtils.isEmpty(product_id)) {
      throw new BadRequestException(GEN_01.getCode(), GEN_01.getDescription(), "product_id");
    }

    Product byId = productService.getById(product_id);

    if (byId == null) {
      throw new BadRequestException(PRD_01.getCode(), PRD_01.getDescription(), "product_id");
    }

    return ProductDtoConverter.toDtoAllFields(byId);
  }

  @GetMapping("/inCategory/{category_id}")
  @ApiOperation(value = "Get a lit of Products of Categories")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return a list of Product Objects"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public DtoPage<ProductDto> getByCategoryId(
  @ApiParam(value = "Category ID", required = true) @RequestParam Integer category_id,
  @ApiParam(value = "Inform the page. Starting with 1. Default: 1") @RequestParam(required = false) Integer page,
  @ApiParam(value = "Limit per page.") @RequestParam(required = false) Integer limit,
  @ApiParam(value = "Limit of the description") @RequestParam(required = false) Integer description_length) {
    if (StringUtils.isEmpty(category_id)) {
      throw new BadRequestException(GEN_01.getCode(), GEN_01.getDescription(), "category_id");
    }

    page = page != null ? page : 1;
    limit = limit != null ? limit : Integer.MAX_VALUE;
    int descriptionLength = description_length == null ? Integer.MAX_VALUE : description_length;

    return new DtoPage<>(productService.productsInCategoryCount(category_id),
                         productService.productsInCategory(category_id, page, limit, descriptionLength)
                                       .stream()
                                       .map(o -> ProductDtoConverter.toDto(o, descriptionLength)).collect(toList()));
  }

  @GetMapping("/inDepartment/{department_id}")
  @ApiOperation(value = "Get a list of Products on Department")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return the total and a list of products"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public DtoPage<ProductDto> getByDepartmentId(
  @ApiParam(value = "Department  ID", required = true) @RequestParam Integer department_id,
  @ApiParam(value = "Inform the page. Starting with 1. Default: 1") @RequestParam(required = false) Integer page,
  @ApiParam(value = "Limit per page.") @RequestParam(required = false) Integer limit,
  @ApiParam(value = "Limit of the description") @RequestParam(required = false) Integer description_length) {
    if (StringUtils.isEmpty(department_id)) {
      throw new BadRequestException(GEN_01.getCode(), GEN_01.getDescription(), "department_id");
    }

    page = page != null ? page : 1;
    limit = limit != null ? limit : Integer.MAX_VALUE;
    int descriptionLength = description_length == null ? Integer.MAX_VALUE : description_length;

    return new DtoPage<>(productService.productsInDepartmentCount(department_id),
                         productService.productsInDepartment(department_id, page, limit, descriptionLength)
                                       .stream()
                                       .map(o -> ProductDtoConverter.toDto(o, descriptionLength)).collect(toList()));
  }

  @GetMapping("/{product_id}/details")
  @ApiOperation(value = "Get details of a Product")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return a Object of Products"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public ProductDetailsDto getDetails(@ApiParam(value = "Product ID", required = true) @RequestParam Integer product_id) {
    if (StringUtils.isEmpty(product_id)) {
      throw new BadRequestException(GEN_01.getCode(), GEN_01.getDescription(), "product_id");
    }

    Product byId = productService.getById(product_id);

    if (byId == null) {
      throw new BadRequestException(PRD_01.getCode(), PRD_01.getDescription(), "product_id");
    }

    return ProductDetailsDto.builder()
                            .product_id(byId.getProduct_id())
                            .name(byId.getName())
                            .description(byId.getDescription())
                            .price(ProductDtoConverter.format(byId.getPrice()))
                            .discounted_price(ProductDtoConverter.format(byId.getDiscounted_price()))
                            .image(byId.getImage())
                            .image_2(byId.getImage_2())
                            .build();
  }
}
