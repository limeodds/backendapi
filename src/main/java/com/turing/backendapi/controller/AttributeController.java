package com.turing.backendapi.controller;


import com.turing.backendapi.controller.converter.AttributeDtoConverter;
import com.turing.backendapi.controller.dto.AttributeDto;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.domain.Attribute;
import com.turing.backendapi.service.AttributeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.turing.backendapi.controller.exception.ErrorCodes.ATR_01;
import static com.turing.backendapi.controller.exception.ErrorCodes.ATR_02;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/attributes")
@Api(description = "Everything about Attributes", tags = {"attributes"})
public class AttributeController {

  private final AttributeService attributeService;

  @Autowired
  public AttributeController(AttributeService attributeService) {
    this.attributeService = attributeService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get Attribute list")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "List of Attribute Objects"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public List<AttributeDto> getAll() {
    return attributeService.getAll().stream().map(AttributeDtoConverter::toDto).collect(toList());
  }

  @GetMapping(value = "/{attribute_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get Attribute by ID")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "A object of Attribute"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public AttributeDto getById(@ApiParam(value = "Attribute Id", required = true) @PathVariable("attribute_id") Integer attributeId) {
    if (attributeId == null) {
      throw new BadRequestException(ATR_01.getCode(), ATR_01.getDescription(), "attribute_id");
    }

    Attribute byId = attributeService.getById(attributeId);

    if (byId == null) {
      throw new BadRequestException(ATR_02.getCode(), ATR_02.getDescription(), "attribute_id");
    }

    return AttributeDtoConverter.toDto(byId);
  }
}