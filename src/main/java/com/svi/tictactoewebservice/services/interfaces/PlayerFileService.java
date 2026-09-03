package com.svi.tictactoewebservice.services.interfaces;

import javax.json.JsonObject;
import java.util.List;

public interface PlayerFileService {
    List<JsonObject> listPlayerGames(String playerId);

    List<JsonObject> getAllPlayers();
}
