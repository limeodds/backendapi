package com.turing.backendapi.controller.converter;

import com.turing.backendapi.controller.dto.DtoUtils;
import com.turing.backendapi.controller.dto.TaxDto;
import com.turing.backendapi.domain.Tax;

public class TaxDtoConverter {

  public static TaxDto toDto(Tax domainObj) {
    if (domainObj == null) {
      return null;
    }
    return TaxDto.builder()
                 .tax_id(domainObj.getTax_id())
                 .tax_type(domainObj.getTax_type())
                 .tax_percentage(DtoUtils.format(domainObj.getTax_percentage()))
                 .build();

  }

}
