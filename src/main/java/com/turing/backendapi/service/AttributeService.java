package com.turing.backendapi.service;

import com.turing.backendapi.domain.Attribute;
import com.turing.backendapi.repository.AttributeRepository;
import com.turing.backendapi.service.converter.AttributeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@Transactional(readOnly = true)
@Slf4j
public class AttributeService {


  private final AttributeRepository attributeRepository;

  @Autowired
  public AttributeService(AttributeRepository attributeRepository) {
    this.attributeRepository = attributeRepository;
  }

  public List<Attribute> getAll() {
    return attributeRepository.findAll().stream().map(AttributeConverter::toDomain).collect(toList());
  }

  public Attribute getById(Integer id) {
    return AttributeConverter.toDomain(attributeRepository.findById(id).orElse(null));
  }

}
