package com.svi.tictactoewebservice.models;

public class RoundData {

    private int round;
    private int startMove;
    private Integer endMove;
    private String winner;

    public RoundData(int round, int startMove, Integer endMove, String winner) {
        this.round = round;
        this.startMove = startMove;
        this.endMove = endMove;
        this.winner = winner;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getStartMove() {
        return startMove;
    }

    public void setStartMove(int startMove) {
        this.startMove = startMove;
    }

    public Integer getEndMove() {
        return endMove;
    }

    public void setEndMove(Integer endMove) {
        this.endMove = endMove;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}