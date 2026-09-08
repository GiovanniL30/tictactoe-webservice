package com.svi.tictactoewebservice.dto.request;

import com.svi.tictactoewebservice.constants.ErrorMessages;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class IncreasePlayerScoreRequest {

    @NotBlank(message = ErrorMessages.PLAYER_ID_REQUIRED)
    @Size(min = 3, max = 7, message = ErrorMessages.PLAYER_ID_LENGTH)
    @JsonbProperty("playerid")
    private String playerId;

    @NotBlank(message = ErrorMessages.ROOM_CODE_REQUIRED)
    @Pattern(
            regexp = "^[A-Z0-9]+$",
            message = ErrorMessages.ROOM_CODE_FORMAT
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
