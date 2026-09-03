package com.svi.tictactoewebservice.models;

import com.svi.tictactoewebservice.constants.Symbol;

public class PlayerData {

    private String playerId;
    private int score;
    private int streakCount;
    private Symbol symbol;

    public PlayerData(String playerId, int score, int streakCount, Symbol symbol) {
        this.playerId = playerId;
        this.score = score;
        this.streakCount = streakCount;
        this.symbol = symbol;
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

    public int getStreakCount() {
        return streakCount;
    }

    public void setStreakCount(int streakCount) {
        this.streakCount = streakCount;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}