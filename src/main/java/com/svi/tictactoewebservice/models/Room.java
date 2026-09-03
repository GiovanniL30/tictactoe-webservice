package com.svi.tictactoewebservice.models;

public class Room {

    private final String roomCode;

    private final String gameId;

    public Room(String roomCode, String gameId) {
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
