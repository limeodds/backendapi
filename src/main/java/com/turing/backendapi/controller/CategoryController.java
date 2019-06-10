package com.turing.backendapi.controller;


import static java.util.stream.Collectors.toList;

import com.turing.backendapi.controller.converter.CategoryDtoConverter;
import com.turing.backendapi.controller.dto.CategoryDto;
import com.turing.backendapi.controller.dto.DtoPage;
import com.turing.backendapi.domain.Category;
import com.turing.backendapi.domain.DomainPage;
import com.turing.backendapi.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@Api(value = "Everything about Categories", tags = { "categories" })
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
    public DtoPage<CategoryDto> get(
        @ApiParam(value = "Sorting a field. Allowed fields: 'category_id', 'name'.") @RequestParam(required = false) String orderBy,
        @ApiParam(value = "Inform the page. Starting with 1. Default: 1") @RequestParam(required = false) Integer page,
        @ApiParam(value = "Limit per page, Default: 20.") @RequestParam(required = false) Integer limit ) {

        if(page == null) {
            page = 1;
        }
        if(limit == null){
            limit = 20;
        }

        DomainPage<Category> domainPage = categoryService.getPage(page, limit, orderBy);

        return new DtoPage<>(domainPage.getCount(), domainPage.getRows().stream().map(CategoryDtoConverter::toDto).collect(toList()));
    }
}
