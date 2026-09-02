package com.svi.tictactoewebservice.dto.response;

import com.svi.tictactoewebservice.models.PlayerData;

import java.util.List;

public class GetPlayersResponse extends  ApiResponse{

    private final List<PlayerData> players;

    public GetPlayersResponse(String msg, List<PlayerData> players) {
        super(msg);
        this.players = players;
    }

    public List<PlayerData> getPlayers() {
        return players;
    }

}
