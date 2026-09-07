package com.svi.tictactoewebservice.repositories;

import javax.json.JsonObject;
import java.util.List;

public interface PlayerRepository {

    List<JsonObject> getPlayerGames(String playerId);

    List<JsonObject> listAllPlayers();
}
