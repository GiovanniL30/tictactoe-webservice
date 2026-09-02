package com.svi.tictactoewebservice.services;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.repositories.GameFileRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<JsonObject> getGameIds() {

        List<String> gameIds = gameFileRepository.getGameIds();

        Map<String, List<String>> gamesByRoom = gameIds.stream()
                .collect(Collectors.groupingBy(
                        gameId -> gameId.substring(0, gameId.indexOf("_"))
                ));

        return gamesByRoom.entrySet()
                .stream()
                .map(entry -> {

                    String roomCode = entry.getKey();
                    List<String> roomGames = entry.getValue();

                    JsonArrayBuilder gamesBuilder = Json.createArrayBuilder();

                    roomGames.forEach(gameId -> {
                        String uuid = gameId.substring(
                                gameId.indexOf("_") + 1
                        );

                        gamesBuilder.add(
                                Json.createObjectBuilder()
                                        .add("uuid", uuid)
                                        .add("gameid", gameId)
                        );
                    });

                    return Json.createObjectBuilder()
                            .add("roomcode", roomCode)
                            .add("gamecount", roomGames.size())
                            .add("games", gamesBuilder.build())
                            .build();

                })
                .collect(Collectors.toList());
    }


}
