package com.turing.backendapi.controller;


import com.turing.backendapi.controller.converter.TaxDtoConverter;
import com.turing.backendapi.controller.dto.TaxDto;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.controller.exception.ErrorResponse;
import com.turing.backendapi.domain.Tax;
import com.turing.backendapi.service.TaxService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.turing.backendapi.controller.exception.ErrorCodes.GEN_01;
import static com.turing.backendapi.controller.exception.ErrorCodes.TAX_01;

@RestController
@RequestMapping(value = "/tax", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Taxes", tags = {"tax"})
public class TaxController implements Validation {

  private final TaxService taxService;

  @Autowired
  public TaxController(TaxService taxService) {
    this.taxService = taxService;
  }

  @GetMapping
  @ApiOperation(value = "Get All Taxes", notes = "Return a list of tax.")
  @ApiResponses({
                @ApiResponse(code = 200, message = "A Array of Object Tax", response = TaxDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  public TaxDto[] getAll() {
    return taxService.getAll().stream().map(TaxDtoConverter::toDto).toArray(TaxDto[]::new);
  }

  @GetMapping(value = "/{tax_Id}")
  @ApiOperation(value = "Get Tax by ID", notes = "Return a tax by ID.")
  @ApiResponses({
                @ApiResponse(code = 200, message = "A object of Tax", response = TaxDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({@ApiImplicitParam(name = "tax_Id", value = "ID of Tax", required = true, dataType = "int", paramType = "path")})
  public TaxDto getById(@PathVariable("tax_Id") Integer tax_Id) {
    checkNotEmpty(tax_Id, GEN_01, "tax_Id");

    Tax byId = taxService.getById(tax_Id);

    if (byId == null) {
      throw new BadRequestException(TAX_01.getCode(), TAX_01.getDescription(), "tax_Id");
    }

    return TaxDtoConverter.toDto(byId);
  }
}
