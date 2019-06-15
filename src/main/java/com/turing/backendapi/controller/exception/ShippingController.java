package com.turing.backendapi.controller.exception;


import com.turing.backendapi.controller.Validation;
import com.turing.backendapi.controller.converter.ShippingDtoConverter;
import com.turing.backendapi.controller.converter.ShippingRegionDtoConverter;
import com.turing.backendapi.controller.dto.ShippingDto;
import com.turing.backendapi.controller.dto.ShippingRegionDto;
import com.turing.backendapi.service.ShippingService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.turing.backendapi.controller.exception.ErrorCodes.GEN_01;

@RestController
@RequestMapping(value = "/shipping", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Shippings", tags = {"shipping"})
public class ShippingController implements Validation {

  private final ShippingService shippingService;

  @Autowired
  public ShippingController(ShippingService shippingService) {
    this.shippingService = shippingService;
  }

  @GetMapping(value = "/regions")
  @ApiOperation(value = "Return shipping regions")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a list of Shippings Regions", response = ShippingRegionDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  public ShippingRegionDto[] getAll() {
    return shippingService.getAllShippingRegions().stream().map(ShippingRegionDtoConverter::toDto).toArray(ShippingRegionDto[]::new);
  }

  @GetMapping(value = "/regions/{shipping_region_id}")
  @ApiOperation(value = "Return shipping regions")
  @ApiResponses({
                @ApiResponse(code = 200, message = "Return a list of Object", response = ShippingDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "shipping_region_id", value = "Shipping Region Id", required = true, dataType = "int", paramType = "path")})
  public ShippingDto[] getById(@PathVariable("shipping_region_id") Integer shipping_region_id) {
    checkNotEmpty(shipping_region_id, GEN_01, "shipping_region_id");

    return shippingService.getByShippingRegionId(shipping_region_id).stream()
                          .map(ShippingDtoConverter::toDto).toArray(ShippingDto[]::new);
  }
}
