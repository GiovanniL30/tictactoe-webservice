package com.svi.tictactoewebservice.services;

import com.svi.tictactoewebservice.dto.request.IncreasePlayerScoreRequest;
import com.svi.tictactoewebservice.dto.request.PlayerRequest;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.models.PlayerData;
import com.svi.tictactoewebservice.models.Symbol;
import com.svi.tictactoewebservice.repositories.GameDataRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GameDataService {

    @Inject
    private GameDataRepository gameDataRepository;

    public List<PlayerData> addPlayers(String roomCode, List<PlayerRequest> players) {
        List<PlayerData> transformed = players.stream()
                .map(player -> new PlayerData(player.getPlayerId(),player.getName(), 0, 0, Symbol.fromString(player.getSymbol())))
                .collect(Collectors.toList());
        return gameDataRepository.addPlayers(roomCode, transformed);
    }

    public List<PlayerData> getPlayers(String roomCode) {
        if(gameDataRepository.roomNotExists(roomCode)) {
            throw new RecordNotFoundException(String.format("Room Code '%s' does not exist.", roomCode));
        }

        return gameDataRepository.getPlayers(roomCode);
    }

    public List<PlayerData> deleteRoom(String roomCode) {
        if(gameDataRepository.roomNotExists(roomCode)) {
            throw new RecordNotFoundException(String.format("Room Code '%s' does not exist.", roomCode));
        }

        return gameDataRepository.removeRoom(roomCode);
    }

    public PlayerData increasePlayerScore(IncreasePlayerScoreRequest playerScoreRequest) {
        return gameDataRepository.increasePlayerScore(playerScoreRequest.getRoomCode(), playerScoreRequest.getPlayerId(), 1);
    }


}
