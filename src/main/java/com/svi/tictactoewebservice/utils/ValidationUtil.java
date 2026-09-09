package com.svi.tictactoewebservice.utils;

import com.svi.tictactoewebservice.constants.ErrorMessages;

import java.util.UUID;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T requireNonNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(
                    ErrorMessages.format(ErrorMessages.NULL_VALUE, fieldName)
            );
        }

        return value;
    }

    public static void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    ErrorMessages.format(ErrorMessages.BLANK_TEXT, fieldName)
            );
        }

    }

    public static UUID parseUuid(String value, String fieldName) {
        requireText(value, fieldName);

        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    ErrorMessages.format(ErrorMessages.UUID_FORMAT, fieldName),
                    exception
            );
        }
    }
}
