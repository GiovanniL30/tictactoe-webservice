package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.models.Room;
import java.util.List;
import java.util.Map;

public interface PlayerRepository {

    List<String> getPlayerGames(String playerId);

    Map<String, List<Room>> listAllPlayers();
}
