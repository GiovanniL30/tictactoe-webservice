package com.svi.tictactoewebservice.services.implementations;

import com.svi.tictactoewebservice.constants.Symbol;
import com.svi.tictactoewebservice.dto.request.IncreasePlayerScoreRequest;
import com.svi.tictactoewebservice.dto.request.PlayerRequest;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.models.PlayerData;
import com.svi.tictactoewebservice.repositories.GameMetadataRepository;
import com.svi.tictactoewebservice.services.interfaces.GameMetadataService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class GameMetadataServiceImpl implements GameMetadataService {

    private final GameMetadataRepository gameMetadataRepository;

    @Inject
    public GameMetadataServiceImpl(GameMetadataRepository gameMetadataRepository) {
        this.gameMetadataRepository = gameMetadataRepository;
    }

    @Override
    public PlayerData addPlayer(String roomCode, PlayerRequest player) {
        return gameMetadataRepository.addPlayer(roomCode, new PlayerData(player.getPlayerId(), 0, 0, Symbol.fromString(player.getSymbol())));
    }

    @Override
    public List<PlayerData> getPlayers(String roomCode) {
        if (gameMetadataRepository.roomNotExists(roomCode)) {
            throw new RecordNotFoundException(String.format("Room Code '%s' does not exist.", roomCode));
        }

        return gameMetadataRepository.getPlayers(roomCode);
    }

    @Override
    public List<PlayerData> deleteRoom(String roomCode) {
        if (gameMetadataRepository.roomNotExists(roomCode)) {
            throw new RecordNotFoundException(String.format("Room Code '%s' does not exist.", roomCode));
        }

        return gameMetadataRepository.removeRoom(roomCode);
    }

    @Override
    public PlayerData increasePlayerScore(IncreasePlayerScoreRequest playerScoreRequest) {
        return gameMetadataRepository.increasePlayerScore(playerScoreRequest.getRoomCode(), playerScoreRequest.getPlayerId(), 1);
    }

    @Override
    public Room generateRoomKeys() {
        return gameMetadataRepository.generateRoomKeys();
    }

    @Override
    public String getRoomUUID(String roomCode) {
        return gameMetadataRepository.getRoomUUID(roomCode);
    }

    @Override
    public String removeGameUUID(String roomCode) {
        if (gameMetadataRepository.roomNotExists(roomCode)) {
            throw new RecordNotFoundException(String.format("Room Code '%s' does not exist.", roomCode));
        }

        return gameMetadataRepository.regenerateGameUUID(roomCode);
    }

}
