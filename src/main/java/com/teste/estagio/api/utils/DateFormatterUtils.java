package com.teste.estagio.api.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatterUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }
}
