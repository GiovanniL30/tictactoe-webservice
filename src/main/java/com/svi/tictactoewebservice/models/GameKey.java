package com.svi.tictactoewebservice.models;

public class GameKey {

    private final String roomCode;

    private final String gameId;

    public GameKey(String roomCode, String gameId) {
        this.roomCode = roomCode;
        this.gameId = gameId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getGameId() {
        return gameId;
    }
}
