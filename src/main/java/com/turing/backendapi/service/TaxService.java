package com.turing.backendapi.service;

import com.turing.backendapi.domain.Tax;
import com.turing.backendapi.repository.TaxRepository;
import com.turing.backendapi.service.converter.TaxConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@Transactional(readOnly = true)
public class TaxService {
  private final TaxRepository taxRepository;

  @Autowired
  public TaxService(TaxRepository taxRepository) {
    this.taxRepository = taxRepository;
  }

  public List<Tax> getAll() {
    return taxRepository.findAll().stream().map(TaxConverter::toDomain).collect(toList());
  }

  public Tax getById(Integer id) {
    return TaxConverter.toDomain(taxRepository.findById(id).orElse(null));
  }
}
