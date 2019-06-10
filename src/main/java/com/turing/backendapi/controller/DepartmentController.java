package com.turing.backendapi.controller;


import com.turing.backendapi.controller.converter.DepartmentDtoConverter;
import com.turing.backendapi.controller.dto.DepartmentDto;
import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.controller.exception.ErrorCodes;
import com.turing.backendapi.domain.Department;
import com.turing.backendapi.service.DepartmentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.turing.backendapi.controller.exception.ErrorCodes.DEP_01;
import static com.turing.backendapi.controller.exception.ErrorCodes.DEP_02;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/departments")
@Api(value = "Everything about Department")
public class DepartmentController {
  private final DepartmentService departmentService;

  @Autowired
  public DepartmentController(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get Departments")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "An Array of Object Department"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public List<DepartmentDto> getAll() {
    return departmentService.getAll().stream().map(DepartmentDtoConverter::toDto).collect(toList());
  }

  @GetMapping(value="/{department_id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get Department by ID")
  @ApiResponses(value = {
  @ApiResponse(code = 200, message = "A object of Department"),
  @ApiResponse(code = 400, message = "Return a error object")
  }
  )
  public DepartmentDto getById (@PathVariable("department_id") String department_id){
    Integer departmentId;
    try {
      departmentId = Integer.parseInt(department_id);
    } catch (NumberFormatException e){
      throw new BadRequestException(DEP_01.getCode(), DEP_01.getDescription(), "department_id");
    }

    Department byId = departmentService.getById(departmentId);

    if(byId == null){
      throw new BadRequestException(DEP_02.getCode(), DEP_02.getDescription(), "department_id");
    }

    return DepartmentDtoConverter.toDto(byId);
  }
}
