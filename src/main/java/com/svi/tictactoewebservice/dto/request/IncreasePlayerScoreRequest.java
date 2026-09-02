package com.svi.tictactoewebservice.dto.request;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class IncreasePlayerScoreRequest {

    @NotBlank
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "Player ID must be a valid UUID."
    )
    @JsonbProperty("playerid")
    private String playerId;

    @NotBlank(message = "Room code is required.")
    @Pattern(
            regexp = "^[A-Z0-9]+$",
            message = "Room code must contain only uppercase letters and numbers."
    )
    @JsonbProperty("roomcode")
    private String roomCode;

    public IncreasePlayerScoreRequest(){
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
}
