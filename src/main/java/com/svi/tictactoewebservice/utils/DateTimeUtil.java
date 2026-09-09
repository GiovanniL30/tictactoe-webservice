package com.svi.tictactoewebservice.utils;

import com.svi.tictactoewebservice.constants.ErrorMessages;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
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

    public static String format(Date datetime) {
        return format(datetime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public static Date parseDate(String datetime) {
        LocalDateTime parsed = parse(datetime)
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.DATETIME_FORMAT));
        return Date.from(parsed.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Optional<LocalDateTime> parse(String datetime) {
        try {
            return Optional.of(LocalDateTime.parse(datetime, FORMATTER));
        } catch (DateTimeParseException | NullPointerException e) {
            return Optional.empty();
        }
    }
}
