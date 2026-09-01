package com.svi.tictactoewebservice.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public final class DateTimeUtil {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private DateTimeUtil() {
        // Utility class
    }

    public static String format(LocalDateTime datetime) {
        return datetime.format(FORMATTER);
    }

    public static String now() {
        return format(LocalDateTime.now());
    }

    public static Optional<LocalDateTime> parse(String datetime) {
        try {
            return Optional.of(LocalDateTime.parse(datetime, FORMATTER));
        } catch (DateTimeParseException | NullPointerException e) {
            return Optional.empty();
        }
    }

    public static boolean isValid(String datetime) {
        return parse(datetime).isPresent();
    }
}