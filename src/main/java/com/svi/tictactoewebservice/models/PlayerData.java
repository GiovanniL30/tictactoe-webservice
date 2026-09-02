package com.svi.tictactoewebservice.models;

public class PlayerData {

    private String uuid;
    private String name;
    private int score;
    private int streakCount;
    private Symbol symbol;

    public PlayerData(String uuid, String name, int score,  int streakCount, Symbol symbol) {
        this.uuid = uuid;
        this.name = name;
        this.score = score;
        this.symbol = symbol;
        this.streakCount = streakCount;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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