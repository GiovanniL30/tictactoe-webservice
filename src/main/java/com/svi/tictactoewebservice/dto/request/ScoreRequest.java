package com.svi.tictactoewebservice.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.Pattern;

public class ScoreRequest {

    @NotBlank
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "Player ID must be a valid UUID."
    )
    @JsonbProperty("playerid")
    private String playerId;

    @Min(value = 0, message = "Score cannot be negative.")
    @JsonbProperty("score")
    private int score;

    public ScoreRequest() {
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}