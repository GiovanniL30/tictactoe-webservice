package com.svi.tictactoewebservice.config;

import com.svi.tictactoewebservice.constants.ErrorMessages;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException(ErrorMessages.CONFIG_FILE_NOT_FOUND);
            }

            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new RuntimeException(ErrorMessages.CONFIG_FILE_LOAD_FAILED, e);
        }
    }

    private Config() {
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isEmpty()) {
            throw new RuntimeException(ErrorMessages.format(ErrorMessages.CONFIG_PROPERTY_NOT_FOUND, key));
        }

        return value;
    }

    public enum Key {
        GAME_RECORDS_PATH,
        PLAYER_RECORDS_PATH,
        ROOMS_RECORDS_PATH,
        ALLOWED_ORIGINS;

        public String value() {
            return name();
        }
    }
}
