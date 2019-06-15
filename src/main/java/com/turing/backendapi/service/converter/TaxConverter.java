package com.turing.backendapi.service.converter;

import com.turing.backendapi.domain.Tax;
import com.turing.backendapi.repository.entity.TaxEntity;

public class TaxConverter {

  public static TaxEntity toDb(Tax domainObj) {
    if (domainObj == null) {
      return null;
    }
    return TaxEntity.builder()
                    .tax_id(domainObj.getTax_id())
                    .tax_type(domainObj.getTax_type())
                    .tax_percentage(domainObj.getTax_percentage())
                    .build();

  }

  public static Tax toDomain(TaxEntity entityObj) {
    if (entityObj == null) {
      return null;
    }
    return Tax.builder()
              .tax_id(entityObj.getTax_id())
              .tax_type(entityObj.getTax_type())
              .tax_percentage(entityObj.getTax_percentage())
              .build();

  }
}
