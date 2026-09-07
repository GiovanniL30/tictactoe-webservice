package com.svi.tictactoewebservice.services;

import javax.json.JsonObject;
import java.util.List;

public interface PlayerService {
    List<JsonObject> listPlayerGames(String playerId);

    List<JsonObject> getAllPlayers();
}
