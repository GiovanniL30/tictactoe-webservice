package com.svi.tictactoewebservice.services;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.exceptions.SymbolAlreadyTakenException;
import com.svi.tictactoewebservice.models.Move;
import com.svi.tictactoewebservice.repositories.GameFileRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class GameFileService {

    private final Map<String, List<Move>> gameIdMoveCache = new HashMap<>();

    @Inject
    private GameFileRepository gameFileRepository;

    public void saveMove(SaveMoveRequest request) {
        List<Move> gameMoves = gameIdMoveCache.computeIfAbsent(request.getGameId(), key -> new ArrayList<>());

        boolean positionTaken = gameMoves
                .stream()
                .anyMatch(move -> move.getPosition() == request.getLocation());

        if (positionTaken) {
            throw new SymbolAlreadyTakenException("Failed to place move, position is already taken");
        }

        gameFileRepository.savePlayerMoveOnTxt(
                request.getPlayerId(),
                request.getGameId()
        );

        gameFileRepository.saveGameMoveOnTxt(request);

        gameFileRepository.saveRoomOnTxt(
                request.getRoomCode(),
                request.getGameId()
        );

        gameMoves.add(new Move(
                request.getPlayerId(),
                request.getLocation()
        ));
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

    public List<JsonObject> getGameIds() {

        Map<String, List<String>> gamesByRoom =
                gameFileRepository.getGamesByRoom();

        return gamesByRoom.entrySet()
                .stream()
                .map(entry -> {

                    String roomCode = entry.getKey();
                    List<String> gameIds = entry.getValue();

                    JsonArrayBuilder gamesBuilder =
                            Json.createArrayBuilder();

                    gameIds.forEach(gameId -> {

                        gamesBuilder.add(
                                Json.createObjectBuilder()
                                        .add("gameid", gameId)
                        );
                    });

                    return Json.createObjectBuilder()
                            .add("roomcode", roomCode)
                            .add("gamecount", gameIds.size())
                            .add("games", gamesBuilder.build())
                            .build();

                })
                .collect(Collectors.toList());
    }

    public List<JsonObject> getAllPlayers() {
        return gameFileRepository.listAllPlayers();
    }


}
