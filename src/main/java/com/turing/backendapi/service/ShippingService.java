package com.turing.backendapi.service;

import com.turing.backendapi.domain.Shipping;
import com.turing.backendapi.domain.ShippingRegion;
import com.turing.backendapi.repository.ShippingRepository;
import com.turing.backendapi.service.converter.ShippingConverter;
import com.turing.backendapi.service.converter.ShippingRegionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@Transactional(readOnly = true)
public class ShippingService {
  private final ShippingRepository shippingRepository;

  @Autowired
  public ShippingService(ShippingRepository shippingRepository) {
    this.shippingRepository = shippingRepository;
  }

  public List<ShippingRegion> getAllShippingRegions() {
    return shippingRepository.findAllShippingRegions().stream()
                             .map(ShippingRegionConverter::toDomain).collect(toList());
  }

  public List<Shipping> getByShippingRegionId(Integer shippingRegionId) {
    return shippingRepository.findByShippingRegionId(shippingRegionId).stream()
                             .map(ShippingConverter::toDomain).collect(toList());
  }
}
