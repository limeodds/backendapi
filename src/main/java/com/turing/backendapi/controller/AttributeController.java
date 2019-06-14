package com.turing.backendapi.controller;


import static com.turing.backendapi.controller.exception.ErrorCodes.ATR_01;
import static com.turing.backendapi.controller.exception.ErrorCodes.ATR_02;
import static java.util.stream.Collectors.toList;

import com.turing.backendapi.controller.converter.AttributeDtoConverter;
import com.turing.backendapi.controller.converter.AttributeValueDtoConverter;
import com.turing.backendapi.controller.dto.AttributeDto;
import com.turing.backendapi.controller.dto.AttributeValueDto;
import com.turing.backendapi.controller.dto.ProductAttributeDto;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.domain.Attribute;
import com.turing.backendapi.service.AttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/attributes", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Attributes", tags = {"attributes"})
public class AttributeController implements Validation {

    private final AttributeService attributeService;

    @Autowired
    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @GetMapping
    @ApiOperation(value = "Get Attribute list")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "List of Attribute Objects"),
        @ApiResponse(code = 400, message = "Return a error object")})
    public List<AttributeDto> getAll() {
        return attributeService.getAll().stream().map(AttributeDtoConverter::toDto).collect(toList());
    }

    @GetMapping(value = "/{attribute_id}")
    @ApiOperation(value = "Get Attribute by ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "A object of Attribute"),
        @ApiResponse(code = 400, message = "Return a error object")})
    public AttributeDto getById(@ApiParam(value = "Attribute Id", required = true) @PathVariable("attribute_id") Integer attributeId) {
        checkNotEmpty(attributeId, ATR_01, "attribute_id");

        Attribute byId = attributeService.getById(attributeId);

        if (byId == null) {
            throw new BadRequestException(ATR_02.getCode(), ATR_02.getDescription(), "attribute_id");
        }

        return AttributeDtoConverter.toDto(byId);
    }

    @GetMapping(value = "/values/{attribute_id}")
    @ApiOperation(value = "Get Values Attribute from Attribute")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return a list of Attribute Values"),
        @ApiResponse(code = 400, message = "Return a error object")})
    public List<AttributeValueDto> getValues(@ApiParam(value = "Attribute Id", required = true) @PathVariable("attribute_id") Integer attributeId) {
        checkNotEmpty(attributeId, ATR_01, "attribute_id");

        return attributeService.getAttributeValuesByAttributeId(attributeId).stream()
            .map(AttributeValueDtoConverter::toDto).collect(toList());
    }

    @GetMapping(value = "/inProduct/{product_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all Attributes with Product ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Return a array of Values of Attribute Objects"),
        @ApiResponse(code = 400, message = "Return a error object")})
    public List<ProductAttributeDto> getProductAttributes(@ApiParam(value = "Product Id", required = true) @PathVariable("product_id") Integer product_id) {
        checkNotEmpty(product_id, ATR_01, "product_id");

        return attributeService.getAttributeValuesByProductId(product_id).stream()
            .map(o -> ProductAttributeDto.builder()
                .attribute_name(o.getName())
                .attribute_value_id(o.getAttribute_value_id())
                .attribute_value(o.getValue())
                .build())
            .collect(toList());
    }
}
