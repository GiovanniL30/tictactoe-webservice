package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.models.PlayerData;
import com.svi.tictactoewebservice.models.Room;

import java.util.List;

public interface GameMetadataRepository {

    PlayerData addPlayer(String roomCode, PlayerData player);

    List<PlayerData> getPlayers(String roomCode);

    List<PlayerData> removeRoom(String roomCode);

    String regenerateGameUUID(String roomCode);

    PlayerData increasePlayerScore(String roomCode, String playerId, int count);

    boolean roomNotExists(String roomCode);

    Room generateRoomKeys();

    String getRoomUUID(String roomCode);

}
