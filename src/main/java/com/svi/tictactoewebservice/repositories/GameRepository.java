package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.models.GameMove;
import com.svi.tictactoewebservice.models.Room;

import java.util.List;
import java.util.Map;

public interface GameRepository {

    void saveMove(SaveMoveRequest request, int moveNumber);

    List<GameMove> getGameMoves(String gameId);

    Map<String, List<Room>> getAllRoomGames();
}
