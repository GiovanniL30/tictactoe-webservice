package com.svi.tictactoewebservice.services.interfaces;

import com.svi.tictactoewebservice.dto.request.IncreasePlayerScoreRequest;
import com.svi.tictactoewebservice.dto.request.PlayerRequest;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.models.PlayerData;

import java.util.List;

public interface GameMetadataService {

    PlayerData addPlayer(String roomCode, PlayerRequest player);

    List<PlayerData> getPlayers(String roomCode);

    List<PlayerData> deleteRoom(String roomCode);

    PlayerData increasePlayerScore(IncreasePlayerScoreRequest playerScoreRequest);

    Room generateRoomKeys();

    String getRoomUUID(String roomCode);

    String removeGameUUID(String roomCode);
}
