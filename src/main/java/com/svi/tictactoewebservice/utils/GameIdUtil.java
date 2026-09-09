package com.svi.tictactoewebservice.utils;

import com.svi.tictactoewebservice.models.Room;

public final class GameIdUtil {

    private GameIdUtil() {
    }

    public static Room parse(String value) {
        ValidationUtil.requireText(value, "gameId");

        String[] parts = value.split("_", 2);
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new IllegalArgumentException("gameId must follow the format roomCode_UUID");
        }

        ValidationUtil.parseUuid(parts[1], "gameId");
        return new Room(parts[0], parts[1]);
    }
}
