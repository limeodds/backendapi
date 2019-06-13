package com.turing.backendapi.controller.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DtoUtils {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,##0.00");

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(BigDecimal value) {
        return value != null ? DECIMAL_FORMAT.format(value) : null;
    }

    public static String format(LocalDateTime value) {
        return value != null ? value.format(DATE_TIME_FORMATTER) : null;
    }
}
