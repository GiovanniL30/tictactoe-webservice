package com.svi.tictactoewebservice.services;

import com.svi.tictactoewebservice.dto.request.IncreasePlayerScoreRequest;
import com.svi.tictactoewebservice.dto.request.PlayerRequest;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.models.PlayerData;
import com.svi.tictactoewebservice.models.Symbol;
import com.svi.tictactoewebservice.repositories.GameMetadataRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class GameMetadataService {

    @Inject
    private GameMetadataRepository gameMetadataRepository;

    public PlayerData addPlayer(String roomCode, PlayerRequest player) {
        return gameMetadataRepository.addPlayer(roomCode, new PlayerData(player.getPlayerId(), 0, 0, Symbol.fromString(player.getSymbol())));
    }

    public List<PlayerData> getPlayers(String roomCode) {
        if(gameMetadataRepository.roomNotExists(roomCode)) {
            throw new RecordNotFoundException(String.format("Room Code '%s' does not exist.", roomCode));
        }

        return gameMetadataRepository.getPlayers(roomCode);
    }

    public List<PlayerData> deleteRoom(String roomCode) {
        if(gameMetadataRepository.roomNotExists(roomCode)) {
            throw new RecordNotFoundException(String.format("Room Code '%s' does not exist.", roomCode));
        }

        return gameMetadataRepository.removeRoom(roomCode);
    }

    public PlayerData increasePlayerScore(IncreasePlayerScoreRequest playerScoreRequest) {
        return gameMetadataRepository.increasePlayerScore(playerScoreRequest.getRoomCode(), playerScoreRequest.getPlayerId(), 1);
    }


}
