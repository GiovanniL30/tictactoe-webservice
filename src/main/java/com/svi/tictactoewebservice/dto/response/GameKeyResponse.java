package com.svi.tictactoewebservice.dto.response;

import com.svi.tictactoewebservice.models.GameKey;

public class GameKeyResponse extends ApiResponse{

    private final GameKey gameKey;

    public GameKeyResponse(String msg, GameKey gameKey) {
        super(msg);
        this.gameKey = gameKey;
    }

    public GameKey getGameKey() {
        return gameKey;
    }
}
