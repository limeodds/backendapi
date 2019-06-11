package com.turing.backendapi.controller;


import com.turing.backendapi.controller.converter.ProductDtoConverter;
import com.turing.backendapi.controller.dto.DtoPage;
import com.turing.backendapi.controller.dto.ProductDto;
import com.turing.backendapi.domain.DomainPage;
import com.turing.backendapi.domain.Product;
import com.turing.backendapi.service.ProductService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/products")
@Api(description = "Everything about Products", tags = {"products"})
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get All Products")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return the total of products and a list of Products in row."),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public DtoPage<ProductDto> getCategories(
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

}
