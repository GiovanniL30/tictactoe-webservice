package com.svi.tictactoewebservice.services;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.repositories.GameRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.List;

@ApplicationScoped
public class GameService {

    @Inject
    private GameRepository gameRepository;

    public void saveMove(SaveMoveRequest request) {
        gameRepository.savePlayerGame(
                request.getPlayerId(),
                request.getGameId()
        );

        gameRepository.saveGameMove(request);
    }

    public List<JsonObject> listPlayerGames(String playerId) {
        if (gameRepository.playerNotExists(playerId)) {
            throw new RecordNotFoundException("Record not found");
        }

        return gameRepository.getPlayerGames(playerId);
    }

    public List<JsonObject> listGameMoves(String playerId) {
        if (gameRepository.gameNotExists(playerId)) {
            throw new RecordNotFoundException("Record not found");
        }

        return gameRepository.getGameMoves(playerId);
    }


}
