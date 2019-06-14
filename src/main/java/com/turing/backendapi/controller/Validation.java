package com.turing.backendapi.controller;

import com.turing.backendapi.controller.exception.BadRequestException;
import com.turing.backendapi.controller.exception.ErrorCodes;
import org.springframework.util.StringUtils;

public interface Validation {

    default void checkNotEmpty(String valueToCheck, ErrorCodes err, String fieldName) {
        if (StringUtils.isEmpty(valueToCheck)) {
            throw new BadRequestException(err.getCode(), err.getDescription(), fieldName);
        }
    }

    default void checkNotEmpty(Integer valueToCheck, ErrorCodes err, String fieldName) {
        if (StringUtils.isEmpty(valueToCheck)) {
            throw new BadRequestException(err.getCode(), err.getDescription(), fieldName);
        }
    }
}
