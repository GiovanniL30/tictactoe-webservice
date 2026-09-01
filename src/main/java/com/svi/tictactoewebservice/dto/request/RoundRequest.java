package com.svi.tictactoewebservice.dto.request;

import javax.validation.constraints.Min;
import javax.json.bind.annotation.JsonbProperty;

public class RoundRequest {

    @Min(value = 1, message = "Round must be at least 1.")
    @JsonbProperty("round")
    private int round;

    public RoundRequest() {
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}