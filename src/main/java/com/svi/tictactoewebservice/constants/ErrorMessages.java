package com.svi.tictactoewebservice.constants;

public final class ErrorMessages {

    public static final String CASSANDRA_SESSION_NOT_INITIALIZED= "CassandraConnection has not been initialized. Call initialize() first.";

    public static final String CONFIG_FILE_NOT_FOUND = "config.properties not found.";
    public static final String CONFIG_FILE_LOAD_FAILED = "Failed to load config.properties.";
    public static final String CONFIG_PROPERTY_NOT_FOUND = "Configuration property not found: %s";
    public static final String RECORDS_DIRECTORY_INITIALIZATION_FAILED = "Failed to initialize records directories.";

    public static final String ROOM_RECORDS_RETRIEVAL_FAILED = "Failed to retrieve room records.";
    public static final String ROOM_RECORD_READ_FAILED = "Failed to read room record.";
    public static final String RECORD_CHECK_FAILED = "Failed to check record.";
    public static final String GAME_MOVES_RETRIEVAL_FAILED = "Failed to retrieve game moves.";
    public static final String PLAYER_GAME_SAVE_FAILED = "Failed to save player game record.";
    public static final String GAME_MOVE_SAVE_FAILED = "Failed to save game move record.";
    public static final String ROOM_GAME_SAVE_FAILED = "Failed to save room game record.";
    public static final String PLAYER_GAMES_RETRIEVAL_FAILED = "Failed to retrieve player games.";
    public static final String PLAYER_RECORDS_RETRIEVAL_FAILED = "Failed to retrieve player records.";
    public static final String PLAYER_RECORD_READ_FAILED = "Failed to read player record: %s";

    public static final String RECORD_NOT_FOUND = "Record not found.";
    public static final String ROOM_NOT_FOUND = "Room code does not exist.";
    public static final String ROOM_NOT_FOUND_WITH_CODE = "Room code '%s' does not exist.";
    public static final String PLAYER_NOT_IN_ROOM = "Player does not exist in the room.";
    public static final String PLAYER_ALREADY_IN_ROOM = "Player already exists in the room.";
    public static final String SYMBOL_ALREADY_TAKEN = "Symbol %s is already taken.";
    public static final String POSITION_ALREADY_TAKEN = "Failed to place move; position is already taken.";
    public static final String X_PLAYER_MUST_JOIN_FIRST = "Player X must be the first player to join the room.";
    public static final String ROOM_FULL = "Room already contains 2 players. Cannot add more players.";

    public static final String VALIDATION_FAILED = "Validation failed.";
    public static final String UNEXPECTED_SERVER_ERROR = "The server encountered an unexpected error.";
    public static final String GAME_ID_REQUIRED = "Game ID is required.";
    public static final String GAME_ID_FORMAT = "Game ID must follow the format roomCode_UUID.";
    public static final String SYMBOL_REQUIRED = "Symbol is required.";
    public static final String SYMBOL_FORMAT = "Symbol must be either X or O.";
    public static final String LOCATION_REQUIRED = "Location is required.";
    public static final String LOCATION_RANGE = "Location must be between 0 and 8.";
    public static final String PLAYER_ID_REQUIRED = "Player ID is required.";
    public static final String PLAYER_ID_LENGTH = "Player ID must be between 3 and 7 characters.";
    public static final String DATETIME_REQUIRED = "Datetime is required.";
    public static final String DATETIME_FORMAT = "Datetime must follow the format yyyy-MM-dd HH:mm:ss.";
    public static final String ROOM_CODE_REQUIRED = "Room code is required.";
    public static final String ROOM_CODE_FORMAT = "Room code must contain only uppercase letters and numbers.";

    private ErrorMessages() {
    }

    public static String format(String message, Object... arguments) {
        return String.format(message, arguments);
    }
}
