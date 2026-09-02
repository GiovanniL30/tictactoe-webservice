package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.exceptions.PlayerAlreadyExistsException;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.exceptions.RoomAlreadyFullException;
import com.svi.tictactoewebservice.exceptions.SymbolAlreadyTakenException;
import com.svi.tictactoewebservice.models.PlayerData;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class GameMetadataRepository {

    // Room code : Players
    private final Map<String, List<PlayerData>> rooms = new HashMap<>();


    public PlayerData addPlayer(String roomCode, PlayerData player) {
        validateRoomNotFull(roomCode);

        List<PlayerData> players = rooms.get(roomCode);

        if (players != null) {
            boolean playerExists = players.stream()
                    .anyMatch(existingPlayer ->
                            existingPlayer.getPlayerId().equals(player.getPlayerId()));

            if (playerExists) {
                throw new PlayerAlreadyExistsException(
                        "Player already exists in the room."
                );
            }

            boolean symbolExists = players.stream()
                    .anyMatch(existingPlayer ->
                            existingPlayer.getSymbol() == player.getSymbol());

            if (symbolExists) {
                throw new SymbolAlreadyTakenException(
                        "Symbol " + player.getSymbol() + " is already taken."
                );
            }
        }

        if (players == null) {
            players = new ArrayList<>();
            rooms.put(roomCode, players);
        }

        players.add(player);
        return player;
    }

    public List<PlayerData> getPlayers(String roomCode) {
        return rooms.get(roomCode);
    }

    public List<PlayerData> removeRoom(String roomCode) {
        return rooms.remove(roomCode);
    }

    public PlayerData increasePlayerScore(String roomCode, String playerId, int count) {
        if(roomNotExists(roomCode)) {
            throw  new RecordNotFoundException("Room Code does not exists.");
        }

        List<PlayerData> players = rooms.get(roomCode);

        PlayerData player = players.stream()
                .filter(p -> p.getPlayerId().equals(playerId))
                .findFirst()
                .orElseThrow(() ->
                        new RecordNotFoundException("Player does not exist in the room."));

        player.setScore(player.getScore() + count);

        return player;
    }

    public boolean roomNotExists(String roomCode) {
        return roomCode == null
                || roomCode.isEmpty()
                || !rooms.containsKey(roomCode);
    }

    private void validateRoomNotFull(String roomCode) {
        if (!roomNotExists(roomCode) && rooms.get(roomCode).size() >= 2) {
            throw new RoomAlreadyFullException(
                    "Room already contains 2 players. Cannot add more players."
            );
        }
    }
}