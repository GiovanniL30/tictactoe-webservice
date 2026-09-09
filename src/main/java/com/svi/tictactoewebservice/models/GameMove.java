package com.svi.tictactoewebservice.models;

public class GameMove {

    private final String gameId;
    private final int moveNumber;
    private final String playerId;
    private final String symbol;
    private final int location;
    private final String dateSave;

    public GameMove(String gameId, String playerId, String symbol, int location, String dateSave) {
        this(gameId, 0, playerId, symbol, location, dateSave);
    }

    public GameMove(String gameId, int moveNumber, String playerId, String symbol, int location, String dateSave) {
        this.gameId = gameId;
        this.moveNumber = moveNumber;
        this.playerId = playerId;
        this.symbol = symbol;
        this.location = location;
        this.dateSave = dateSave;
    }

    public String getGameId() {
        return gameId;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getLocation() {
        return location;
    }

    public String getDateSave() {
        return dateSave;
    }
}
