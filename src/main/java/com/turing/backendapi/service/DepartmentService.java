package com.turing.backendapi.service;

import com.turing.backendapi.domain.Department;
import com.turing.backendapi.repository.DepartmentRepository;
import com.turing.backendapi.service.converter.DepartmentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@Transactional(readOnly = true)
public class DepartmentService {
  private final DepartmentRepository departmentRepository;

  @Autowired
  public DepartmentService(DepartmentRepository departmentRepository) {
    this.departmentRepository = departmentRepository;
  }

  public List<Department> getAll() {
    return departmentRepository.findAll().stream().map(DepartmentConverter::toDomain).collect(toList());
  }

  public Department getById(Integer id){
    return DepartmentConverter.toDomain(departmentRepository.findById(id).orElse(null));
  }
}
