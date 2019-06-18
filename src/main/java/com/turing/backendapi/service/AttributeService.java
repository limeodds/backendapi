package com.turing.backendapi.service;

import com.turing.backendapi.domain.Attribute;
import com.turing.backendapi.domain.AttributeValue;
import com.turing.backendapi.domain.Category;
import com.turing.backendapi.domain.ProductAttribute;
import com.turing.backendapi.repository.AttributeRepository;
import com.turing.backendapi.repository.AttributeValueRepository;
import com.turing.backendapi.service.converter.AttributeConverter;
import com.turing.backendapi.service.converter.AttributeValueConverter;
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
  private final AttributeValueRepository attributeValueRepository;

  @Autowired
  public AttributeService(AttributeRepository attributeRepository, AttributeValueRepository attributeValueRepository) {
    this.attributeRepository = attributeRepository;
    this.attributeValueRepository = attributeValueRepository;
  }

  public List<Attribute> getAll() {
    log.info("getAll()");
    return attributeRepository.findAll().stream().map(AttributeConverter::toDomain).collect(toList());
  }

  public Attribute getById(Integer id) {
    log.info("getById(id: {})", id);
    return AttributeConverter.toDomain(attributeRepository.findById(id).orElse(null));
  }

  public List<AttributeValue> getAttributeValuesByAttributeId(Integer attributeId) {
    log.info("getAttributeValuesByAttributeId(attributeId: {})", attributeId);
    return attributeValueRepository.findByAttributeId(attributeId).stream().map(AttributeValueConverter::toDomain).collect(toList());
  }

  public List<ProductAttribute> getAttributeValuesByProductId(int productId) {
    log.info("getAttributeValuesByProductId(productId: {})", productId);
    return attributeValueRepository.findByProductId(productId).stream()
                             .map(o -> (Object[]) o).map(o -> ProductAttribute.builder()
                                                                              .name((String) o[0])
                                                                              .attribute_value_id((Integer) o[1])
                                                                              .value((String) o[2]).build())
                             .collect(toList());
  }
}
