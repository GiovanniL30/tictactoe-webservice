package com.svi.tictactoewebservice.services;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.repositories.GameRepository;
import com.svi.tictactoewebservice.utils.DateTimeUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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


}
