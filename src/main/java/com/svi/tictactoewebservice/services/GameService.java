package com.svi.tictactoewebservice.services;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;

import javax.json.JsonObject;
import java.util.List;

public interface GameService {

    void saveMove(SaveMoveRequest request);

    List<JsonObject> listGameMoves(String playerId);

    List<JsonObject> getGameIds();

}
