package com.svi.tictactoewebservice.dto.request;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.*;

public class SaveMoveRequest {

    @NotBlank(message = "gameid is required.")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "Game ID must follow UUID format"
    )
    @JsonbProperty("gameid")
    private String gameId;

    @NotBlank(message = "roomcode is required.")
    @Pattern(
            regexp = "^[A-Z0-9]+$",
            message = "Room code must contain only uppercase letters and numbers."
    )
    @JsonbProperty("roomcode")
    private String roomCode;

    @NotBlank
    @Pattern(regexp = "[XO]", message = "Symbol must be either X or O")
    private String symbol;

    @NotNull
    @Min(value = 0, message = "Location must be between 0 and 9.")
    @Max(value = 9, message = "Location must be between 0 and 9.")
    private Integer location;

    @NotBlank(message = "Player ID is required.")
    @Size(min = 3, max = 7, message = "Player ID must be between 3 and 7 characters.")
    @JsonbProperty("playerid")
    private String playerId;

    @NotBlank
    @JsonbProperty("datesave")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$",
            message = "Datetime must follow the format yyyy-MM-dd HH:mm:ss"
    )
    private String datetime;

    public SaveMoveRequest() {
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
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

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
}