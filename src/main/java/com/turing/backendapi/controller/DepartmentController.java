package com.turing.backendapi.controller;


import com.turing.backendapi.controller.converter.DepartmentDtoConverter;
import com.turing.backendapi.controller.dto.DepartmentDto;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.controller.exception.ErrorResponse;
import com.turing.backendapi.domain.Department;
import com.turing.backendapi.service.DepartmentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.turing.backendapi.controller.exception.ErrorCodes.DEP_02;
import static com.turing.backendapi.controller.exception.ErrorCodes.GEN_01;

@RestController
@RequestMapping(value = "/departments", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Everything about Department", tags = {"departments"})
public class DepartmentController implements Validation {

  private final DepartmentService departmentService;

  @Autowired
  public DepartmentController(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @GetMapping
  @ApiOperation(value = "Get Departments", notes = "Return a list of department.")
  @ApiResponses({
                @ApiResponse(code = 200, message = "An Array of Object Department", response = DepartmentDto[].class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  public DepartmentDto[] getAll() {
    return departmentService.getAll().stream().map(DepartmentDtoConverter::toDto).toArray(DepartmentDto[]::new);
  }

  @GetMapping(value = "/{department_id}")
  @ApiOperation(value = "Get Department by ID")
  @ApiResponses({
                @ApiResponse(code = 200, message = "A object of Department", response = DepartmentDto.class),
                @ApiResponse(code = 400, message = "Return a error object", response = ErrorResponse.class)})
  @ApiImplicitParams({
                     @ApiImplicitParam(name = "department_id", value = "ID of Department", required = true, dataType = "int", paramType = "path")})
  public DepartmentDto getById(@PathVariable("department_id") Integer departmentId) {
    checkNotEmpty(departmentId, GEN_01, "department_id");

    Department byId = departmentService.getById(departmentId);

    if (byId == null) {
      throw new BadRequestException(DEP_02.getCode(), DEP_02.getDescription(), "department_id");
    }

    return DepartmentDtoConverter.toDto(byId);
  }
}
