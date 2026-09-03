package com.svi.tictactoewebservice.services.interfaces;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;

import javax.json.JsonObject;
import java.util.List;

public interface GameFileService {

    void saveMove(SaveMoveRequest request);

    List<JsonObject> listGameMoves(String playerId);

    List<JsonObject> getGameIds();

}
