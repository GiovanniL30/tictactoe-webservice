package com.svi.tictactoewebservice.models;

public class Move {

    private final String playerId;
    private final int position;

    public Move(String playerId, int position) {
        this.playerId = playerId;
        this.position = position;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getPosition() {
        return position;
    }

}
