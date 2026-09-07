package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;

import javax.json.JsonObject;
import java.util.List;

public interface GameRepository {

    List<JsonObject> getGameMoves(String gameId);

    void savePlayerMoveOnTxt(String playerId, String gameId);

    void saveGameMoveOnTxt(SaveMoveRequest request);

    void saveRoomOnTxt(String roomCode, String gameId);
}
