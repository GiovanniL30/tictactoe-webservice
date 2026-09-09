package com.svi.tictactoewebservice.utils;

import java.util.UUID;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T requireNonNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }

        return value;
    }

    public static void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }

    }

    public static UUID parseUuid(String value, String fieldName) {
        requireText(value, fieldName);

        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(fieldName + " must be a valid UUID", exception);
        }
    }
}
