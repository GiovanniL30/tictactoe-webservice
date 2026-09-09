package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.models.GameMove;

import java.util.List;

public interface GameRepository {

    List<GameMove> getGameMoves(String gameId);

    void savePlayerGame(String playerId, String gameId, String roomCode);

    void saveMove(SaveMoveRequest request);

    void saveRoomGame(String roomCode, String gameId);
}
