package com.svi.tictactoewebservice.repositories.imp;

import com.svi.tictactoewebservice.constants.Symbol;
import com.svi.tictactoewebservice.constants.ErrorMessages;
import com.svi.tictactoewebservice.exceptions.*;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.models.PlayerData;
import com.svi.tictactoewebservice.repositories.GameMetadataRepository;

import javax.enterprise.context.ApplicationScoped;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class GameMetadataRepositoryImpl implements GameMetadataRepository {

    private final SecureRandom random = new SecureRandom();

    // Room code : Players
    private final Map<String, List<PlayerData>> rooms = new ConcurrentHashMap<>();

    // Room code : Current game ID
    private final Map<String, String> gameIdsByRoomCode = new ConcurrentHashMap<>();


    @Override
    public PlayerData addPlayer(String roomCode, PlayerData player) {
        validateRoomNotFull(roomCode);

        List<PlayerData> players = rooms.get(roomCode);

        if (players != null) {
            boolean playerExists = players.stream()
                    .anyMatch(existingPlayer ->
                            existingPlayer.getPlayerId().equals(player.getPlayerId()));

            if (playerExists) {
                throw new PlayerAlreadyExistsException(
                        ErrorMessages.PLAYER_ALREADY_IN_ROOM
                );
            }

            boolean symbolExists = players.stream()
                    .anyMatch(existingPlayer ->
                            existingPlayer.getSymbol() == player.getSymbol());

            if (symbolExists) {
                throw new SymbolAlreadyTakenException(
                        ErrorMessages.format(ErrorMessages.SYMBOL_ALREADY_TAKEN, player.getSymbol())
                );
            }
        }

        // X must be the first player in the room
        if ((players == null || players.isEmpty())
                && player.getSymbol() != Symbol.X) {
            throw new SymbolOrderException(
                    ErrorMessages.X_PLAYER_MUST_JOIN_FIRST
            );
        }

        if (players == null) {
            players = new ArrayList<>();
            rooms.put(roomCode, players);
        }

        players.add(player);
        return player;
    }

    @Override
    public List<PlayerData> getPlayers(String roomCode) {
        return rooms.get(roomCode);
    }

    @Override
    public void removeRoom(String roomCode) {
        gameIdsByRoomCode.remove(roomCode);
        rooms.remove(roomCode);
    }

    @Override
    public String regenerateGameId(String roomCode) {
        String newGameId = UUID.randomUUID().toString();
        gameIdsByRoomCode.put(roomCode, newGameId);
        return newGameId;
    }

    @Override
    public PlayerData increasePlayerScore(String roomCode, String playerId, int count) {
        if (roomNotExists(roomCode)) {
            throw new RecordNotFoundException(ErrorMessages.ROOM_NOT_FOUND);
        }

        List<PlayerData> players = rooms.get(roomCode);

        PlayerData player = players.stream()
                .filter(p -> p.getPlayerId().equals(playerId))
                .findFirst()
                .orElseThrow(() ->
                        new RecordNotFoundException(ErrorMessages.PLAYER_NOT_IN_ROOM));

        player.setScore(player.getScore() + count);

        return player;
    }

    @Override
    public boolean roomNotExists(String roomCode) {
        return roomCode == null
                || roomCode.isEmpty()
                || !rooms.containsKey(roomCode);
    }

    @Override
    public Room generateRoom() {
        String roomCode = generateRoomCode();

        String gameId = gameIdsByRoomCode.computeIfAbsent(roomCode, key -> UUID.randomUUID().toString());

        return new Room(roomCode, gameId);
    }

    @Override
    public String getGameId(String roomCode) {
        String gameId = gameIdsByRoomCode.get(roomCode);

        if (gameId == null) {
            throw new RecordNotFoundException(
                    ErrorMessages.ROOM_NOT_FOUND
            );
        }

        return gameId;
    }

    private void validateRoomNotFull(String roomCode) {
        if (!roomNotExists(roomCode) && rooms.get(roomCode).size() >= 2) {
            throw new RoomAlreadyFullException(
                    ErrorMessages.ROOM_FULL
            );
        }
    }


    private String generateRoomCode() {
        String roomCodeCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder(4);

        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(roomCodeCharacters.length());
            code.append(roomCodeCharacters.charAt(index));
        }

        return code.toString();
    }
}
