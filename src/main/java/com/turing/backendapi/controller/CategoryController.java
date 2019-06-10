package com.turing.backendapi.controller;


import com.turing.backendapi.controller.converter.CategoryDtoConverter;
import com.turing.backendapi.controller.dto.CategoryDto;
import com.turing.backendapi.controller.dto.CategoryDtoInProduct;
import com.turing.backendapi.controller.dto.DtoPage;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.domain.Category;
import com.turing.backendapi.domain.DomainPage;
import com.turing.backendapi.service.CategoryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.turing.backendapi.controller.exception.ErrorCodes.CAT_01;
import static com.turing.backendapi.controller.exception.ErrorCodes.CAT_02;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/categories")
@Api(description = "Everything about Categories", tags = {"categories"})
public class CategoryController {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get Categories")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return a list with count (total categories) and the rows of Categories"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public DtoPage<CategoryDto> getCategories(
  @ApiParam(value = "Sorting a field. Allowed fields: 'category_id', 'name'.") @RequestParam(required = false) String orderBy,
  @ApiParam(value = "Inform the page. Starting with 1. Default: 1") @RequestParam(required = false) Integer page,
  @ApiParam(value = "Limit per page, Default: 20.") @RequestParam(required = false) Integer limit) {

    if (page == null) {
      page = 1;
    }
    if (limit == null) {
      limit = 20;
    }

    DomainPage<Category> domainPage = categoryService.getPage(page, limit, orderBy);

    return new DtoPage<>(domainPage.getCount(), domainPage.getRows().stream().map(CategoryDtoConverter::toDto).collect(toList()));
  }

  @GetMapping(value = "/{category_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get Category by ID")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return a object of Category"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public CategoryDto getById(@ApiParam(value = "ID of Category", required = true) @PathVariable("category_id") Integer category_id) {
    if (category_id == null) {
      throw new BadRequestException(CAT_02.getCode(), CAT_02.getDescription(), "category_id");
    }

    Category byId = categoryService.getById(category_id);

    if (byId == null) {
      throw new BadRequestException(CAT_01.getCode(), CAT_01.getDescription(), "category_id");
    }

    return CategoryDtoConverter.toDto(byId);
  }

  @GetMapping(value = "/inProduct/{product_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get Categories of a Product")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return a array of Category Objects"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public List<CategoryDtoInProduct> getByProduct(@ApiParam(value = "ID of Product", required = true) @PathVariable("product_id") Integer product_id) {
    if (product_id == null) {
      throw new BadRequestException(CAT_02.getCode(), CAT_02.getDescription(), "product_id");
    }

    List<Category> byId = categoryService.getByProductId(product_id);

    return byId.stream().map(c -> new CategoryDtoInProduct(c.getCategory_id(), c.getDepartment_id(), c.getName())).collect(toList());
  }

  @GetMapping(value = "/inDepartment/{department_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get Categories of a Department")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "Return a array of Object Category"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public List<CategoryDto> getByDepartment(@ApiParam(value = "ID of Department", required = true) @PathVariable("department_id") Integer department_id) {
    if (department_id == null) {
      throw new BadRequestException(CAT_02.getCode(), CAT_02.getDescription(), "department_id");
    }

    return categoryService.getByDepartmentId(department_id).stream()
                          .map(CategoryDtoConverter::toDto).collect(toList());
  }
}
