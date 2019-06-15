package com.turing.backendapi.controller;


import com.turing.backendapi.controller.converter.AttributeDtoConverter;
import com.turing.backendapi.controller.converter.AttributeValueDtoConverter;
import com.turing.backendapi.controller.dto.AttributeDto;
import com.turing.backendapi.controller.dto.AttributeValueDto;
import com.turing.backendapi.controller.dto.ProductAttributeDto;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.controller.exception.ErrorResponse;
import com.turing.backendapi.domain.Attribute;
import com.turing.backendapi.service.AttributeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.turing.backendapi.controller.exception.ErrorCodes.ATR_01;
import static com.turing.backendapi.controller.exception.ErrorCodes.ATR_02;

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
  @ApiResponses({
                @ApiResponse(code = 200, message = "List of Attribute Objects", response = AttributeDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  public AttributeDto[] getAll() {
    return attributeService.getAll().stream().map(AttributeDtoConverter::toDto).toArray(AttributeDto[]::new);
  }

  @GetMapping(value = "/{attribute_id}")
  @ApiOperation(value = "Get Attribute by ID")
  @ApiResponses({
                @ApiResponse(code = 200, message = "A object of Attribute", response = AttributeDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "attribute_id", value = "Attribute ID", required = true, dataType = "int", paramType = "path")})
  public AttributeDto getById(@PathVariable("attribute_id") Integer attributeId) {
    checkNotEmpty(attributeId, ATR_01, "attribute_id");

    Attribute byId = attributeService.getById(attributeId);

    if (byId == null) {
      throw new BadRequestException(ATR_02.getCode(), ATR_02.getDescription(), "attribute_id");
    }

    return AttributeDtoConverter.toDto(byId);
  }

  @GetMapping(value = "/values/{attribute_id}")
  @ApiOperation(value = "Get Values Attribute from Attribute")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a list of Attribute Values", response = AttributeValueDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "attribute_id", value = "Attribute ID", required = true, dataType = "int", paramType = "path")})
  public AttributeValueDto[] getValues(@PathVariable("attribute_id") Integer attributeId) {
    checkNotEmpty(attributeId, ATR_01, "attribute_id");

    return attributeService.getAttributeValuesByAttributeId(attributeId).stream()
                           .map(AttributeValueDtoConverter::toDto).toArray(AttributeValueDto[]::new);
  }

  @GetMapping(value = "/inProduct/{product_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get all Attributes with Product ID")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a array of Values of Attribute Objects", response = ProductAttributeDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "product_id", value = "Product ID", required = true, dataType = "int", paramType = "path")})
  public ProductAttributeDto[] getProductAttributes(@PathVariable("product_id") Integer product_id) {
    checkNotEmpty(product_id, ATR_01, "product_id");

    return attributeService.getAttributeValuesByProductId(product_id).stream()
                           .map(o -> ProductAttributeDto.builder()
                                                        .attribute_name(o.getName())
                                                        .attribute_value_id(o.getAttribute_value_id())
                                                        .attribute_value(o.getValue())
                                                        .build())
                           .toArray(ProductAttributeDto[]::new);
  }
}
