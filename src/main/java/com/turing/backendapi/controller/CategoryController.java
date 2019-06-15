package com.turing.backendapi.controller;


import com.turing.backendapi.controller.converter.CategoryDtoConverter;
import com.turing.backendapi.controller.dto.CategoryDto;
import com.turing.backendapi.controller.dto.CategoryDtoInProduct;
import com.turing.backendapi.controller.dto.DtoCategoryPage;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.controller.exception.ErrorResponse;
import com.turing.backendapi.domain.Category;
import com.turing.backendapi.domain.DomainPage;
import com.turing.backendapi.service.CategoryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.turing.backendapi.controller.exception.ErrorCodes.CAT_01;
import static com.turing.backendapi.controller.exception.ErrorCodes.CAT_02;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Categories", tags = {"categories"})
public class CategoryController implements Validation {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  @ApiOperation(value = "Get Categories")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a list with count (total categories) and the rows of Categories", response = DtoCategoryPage.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "order", value = "Sorting a field. Allowed fields: 'category_id', 'name'.", dataType = "string", paramType = "query"),
                     @ApiImplicitParam(name = "page", value = "Inform the page. Starting with 1. Default: 1", dataType = "int", paramType = "query"),
                     @ApiImplicitParam(name = "limit", value = "Limit per page, Default: 20.", dataType = "int", paramType = "query")})
  public DtoCategoryPage getCategories(@RequestParam(required = false) String order,
                                       @RequestParam(required = false, defaultValue = "1") Integer page,
                                       @RequestParam(required = false, defaultValue = "20") Integer limit) {
    DomainPage<Category> domainPage = categoryService.getPage(page, limit, order);

    return new DtoCategoryPage(domainPage.getCount(), domainPage.getRows().stream().map(CategoryDtoConverter::toDto).collect(toList()));
  }

  @GetMapping(value = "/{category_id}")
  @ApiOperation(value = "Get Category by ID", notes = "Return a category by ID.")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a object of Category", response = CategoryDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "category_id", value = "ID of Category", required = true, dataType = "int", paramType = "path")})
  public CategoryDto getById(@PathVariable("category_id") Integer category_id) {
    checkNotEmpty(category_id, CAT_02, "category_id");

    Category byId = categoryService.getById(category_id);

    if (byId == null) {
      throw new BadRequestException(CAT_01.getCode(), CAT_01.getDescription(), "category_id");
    }

    return CategoryDtoConverter.toDto(byId);
  }

  @GetMapping(value = "/inProduct/{product_id}")
  @ApiOperation(value = "Get Categories of a Product")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a array of Category Objects", response = CategoryDtoInProduct[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "product_id", value = "ID of Product", required = true, dataType = "int", paramType = "path")})
  public CategoryDtoInProduct[] getByProduct(@PathVariable("product_id") Integer product_id) {
    checkNotEmpty(product_id, CAT_02, "product_id");

    return categoryService.getByProductId(product_id)
                          .stream()
                          .map(c -> new CategoryDtoInProduct(c.getCategory_id(), c.getDepartment_id(), c.getName()))
                          .toArray(CategoryDtoInProduct[]::new);
  }

  @GetMapping(value = "/inDepartment/{department_id}")
  @ApiOperation(value = "Get Categories of a Department")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a array of Object Category", response = CategoryDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "department_id", value = "ID of Department", required = true, dataType = "int", paramType = "path")})
  public CategoryDto[] getByDepartment(@PathVariable("department_id") Integer department_id) {
    checkNotEmpty(department_id, CAT_02, "department_id");

    return categoryService.getByDepartmentId(department_id).stream().map(CategoryDtoConverter::toDto).toArray(CategoryDto[]::new);
  }
}
