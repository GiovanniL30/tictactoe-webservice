package com.svi.tictactoewebservice.dto.request;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class IncreasePlayerScoreRequest {

    @NotBlank(message = "Player ID is required.")
    @Size(min = 3, max = 7, message = "Player ID must be between 3 and 7 characters.")
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
