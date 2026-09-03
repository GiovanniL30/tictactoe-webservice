package com.svi.tictactoewebservice.dto.response;

import javax.json.JsonObject;
import java.util.List;

public class GetHistoryPlayersResponse extends  ApiResponse{

    private final List<JsonObject> players;

    public GetHistoryPlayersResponse(String msg, List<JsonObject> players) {
        super(msg);
        this.players = players;
    }

    public List<JsonObject> getPlayers() {
        return players;
    }

}
