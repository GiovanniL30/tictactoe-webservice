package com.svi.tictactoewebservice.models;

import java.util.Map;

public class GameData {

    private final Map<String, GameState> games;

    public GameData(Map<String, GameState> games) {
        this.games = games;
    }
}