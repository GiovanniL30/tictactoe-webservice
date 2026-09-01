package com.svi.tictactoewebservice.services;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.repositories.GameFileRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.List;

@ApplicationScoped
public class GameService {

    @Inject
    private GameFileRepository gameFileRepository;

    public void saveMove(SaveMoveRequest request) {
        gameFileRepository.savePlayerGame(
                request.getPlayerId(),
                request.getGameId()
        );

        gameFileRepository.saveGameMove(request);
    }

    public List<JsonObject> listPlayerGames(String playerId) {
        if (gameFileRepository.playerNotExists(playerId)) {
            throw new RecordNotFoundException("Record not found");
        }

        return gameFileRepository.getPlayerGames(playerId);
    }

    public List<JsonObject> listGameMoves(String playerId) {
        if (gameFileRepository.gameNotExists(playerId)) {
            throw new RecordNotFoundException("Record not found");
        }

        return gameFileRepository.getGameMoves(playerId);
    }


}
