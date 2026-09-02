package com.svi.tictactoewebservice.dto.request;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PlayerRequest {

    @NotBlank(message = "Player ID is required.")
    @Size(min = 3, max = 7, message = "Player ID must be between 3 and 7 characters.")
    @JsonbProperty("playerid")
    private String playerId;

    @NotBlank
    @Pattern(regexp = "[XO]", message = "Symbol must be either X or O")
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
