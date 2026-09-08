package com.svi.tictactoewebservice.services;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;

import com.svi.tictactoewebservice.models.GameMove;
import javax.json.JsonObject;
import java.util.List;

public interface GameService {

    void saveMove(SaveMoveRequest request);

    List<GameMove> listGameMoves(String gameId);

    List<JsonObject> getGameIds();

}
