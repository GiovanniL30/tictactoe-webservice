package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.models.PlayerData;
import com.svi.tictactoewebservice.models.Room;

import java.util.List;

public interface GameMetadataRepository {

    PlayerData addPlayer(String roomCode, PlayerData player);

    List<PlayerData> getPlayers(String roomCode);

    void removeRoom(String roomCode);

    String regenerateGameId(String roomCode);

    PlayerData increasePlayerScore(String roomCode, String playerId, int count);

    boolean roomNotExists(String roomCode);

    Room generateRoom();

    String getGameId(String roomCode);

}
