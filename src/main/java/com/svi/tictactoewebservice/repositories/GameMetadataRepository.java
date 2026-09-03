package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.constants.Symbol;
import com.svi.tictactoewebservice.exceptions.*;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.models.PlayerData;

import javax.enterprise.context.ApplicationScoped;
import java.security.SecureRandom;
import java.util.*;

@ApplicationScoped
public class GameMetadataRepository {

    private final SecureRandom RANDOM = new SecureRandom();

    // Room code : Players
    private final Map<String, List<PlayerData>> rooms = new HashMap<>();

    // Room code : Current Game UUID
    private final Map<String, String> gameUUIDs = new HashMap<>();


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

        // X must be the first player in the room
        if ((players == null || players.isEmpty())
                && player.getSymbol() != Symbol.X) {
            throw new SymbolOrderException(
                    "Player X must be the first player to join the room."
            );
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
        gameUUIDs.remove(roomCode);
        return rooms.remove(roomCode);
    }

    public String regenerateGameUUID(String roomCode) {
        String newGameId = UUID.randomUUID().toString();
        gameUUIDs.put(roomCode, newGameId);
        return newGameId;
    }

    public PlayerData increasePlayerScore(String roomCode, String playerId, int count) {
        if (roomNotExists(roomCode)) {
            throw new RecordNotFoundException("Room Code does not exists.");
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

    public Room generateRoomKeys() {
        String roomCode = generateRoomCode();

        String gameId = gameUUIDs.computeIfAbsent(roomCode, key -> UUID.randomUUID().toString());

        return new Room(roomCode, gameId);
    }

    public String getRoomUUID(String roomCode) {
        String gameId = gameUUIDs.get(roomCode);

        if (gameId == null) {
            throw new RecordNotFoundException(
                    "Room code does not exist."
            );
        }

        return gameId;
    }

    private void validateRoomNotFull(String roomCode) {
        if (!roomNotExists(roomCode) && rooms.get(roomCode).size() >= 2) {
            throw new RoomAlreadyFullException(
                    "Room already contains 2 players. Cannot add more players."
            );
        }
    }


    private String generateRoomCode() {
        String roomCodeCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder(4);

        for (int i = 0; i < 4; i++) {
            int index = RANDOM.nextInt(roomCodeCharacters.length());
            code.append(roomCodeCharacters.charAt(index));
        }

        return code.toString();
    }
}