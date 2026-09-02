package com.svi.tictactoewebservice.dto.response;

import com.svi.tictactoewebservice.models.PlayerData;

public class IncreasePlayerScoreResponse extends ApiResponse {

    private final PlayerData updatedPlayer;

    public IncreasePlayerScoreResponse(String msg, PlayerData updatedPlayer) {
        super(msg);
        this.updatedPlayer = updatedPlayer;
    }

    public PlayerData getUpdatedPlayer() {
        return updatedPlayer;
    }
}
