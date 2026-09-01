package com.svi.tictactoewebservice.dto.request;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SaveMoveRequest {

    @NotBlank
    @JsonbProperty("gameid")
    private String gameId;

    @NotBlank
    @Pattern(regexp = "[XO]", message = "Symbol must be either X or O")
    private String symbol;

    @NotNull
    @Min(0)
    @Max(9)
    private Integer location;

    @NotBlank
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
}