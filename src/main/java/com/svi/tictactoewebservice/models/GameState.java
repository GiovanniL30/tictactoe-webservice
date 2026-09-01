package com.svi.tictactoewebservice.models;

import java.util.List;
import java.util.Map;

public class GameState {

    private int currentRound;
    private Map<String, PlayerData> players;
    private List<RoundData> rounds;

    public GameState(int currentRound, Map<String, PlayerData> players, List<RoundData> rounds) {
        this.currentRound = currentRound;
        this.players = players;
        this.rounds = rounds;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }


    public Map<String, PlayerData> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, PlayerData> players) {
        this.players = players;
    }

    public List<RoundData> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundData> rounds) {
        this.rounds = rounds;
    }
}