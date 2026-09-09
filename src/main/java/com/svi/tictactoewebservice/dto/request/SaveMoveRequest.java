package com.svi.tictactoewebservice.dto.request;

import com.svi.tictactoewebservice.constants.ErrorMessages;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.*;
import java.util.UUID;

public class SaveMoveRequest {

    @NotBlank(message = ErrorMessages.ROOM_CODE_REQUIRED)
    private String roomCode;

    @NotNull(message = ErrorMessages.GAME_ID_REQUIRED)
    private UUID gameId;

    @NotBlank(message = ErrorMessages.SYMBOL_REQUIRED)
    @Pattern(regexp = "[XO]", message = ErrorMessages.SYMBOL_FORMAT)
    private String symbol;

    @NotNull(message = ErrorMessages.LOCATION_REQUIRED)
    @Min(value = 0, message = ErrorMessages.LOCATION_RANGE)
    @Max(value = 8, message = ErrorMessages.LOCATION_RANGE)
    private Integer location;

    @NotBlank(message = ErrorMessages.PLAYER_ID_REQUIRED)
    @Size(min = 3, max = 7, message = ErrorMessages.PLAYER_ID_LENGTH)
    private String playerId;

    @NotBlank(message = ErrorMessages.DATETIME_REQUIRED)
    @JsonbProperty("dateSave")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$",
            message = ErrorMessages.DATETIME_FORMAT
    )
    private String datetime;

    public SaveMoveRequest() {
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

}
