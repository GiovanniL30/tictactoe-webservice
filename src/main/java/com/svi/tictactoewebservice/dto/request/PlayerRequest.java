package com.svi.tictactoewebservice.dto.request;

import com.svi.tictactoewebservice.constants.ErrorMessages;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PlayerRequest {

    @NotBlank(message = ErrorMessages.PLAYER_ID_REQUIRED)
    @Size(min = 3, max = 7, message = ErrorMessages.PLAYER_ID_LENGTH)
    private String playerId;

    @NotBlank(message = ErrorMessages.SYMBOL_REQUIRED)
    @Pattern(regexp = "[XO]", message = ErrorMessages.SYMBOL_FORMAT)
    private String symbol;

    public PlayerRequest() {
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
