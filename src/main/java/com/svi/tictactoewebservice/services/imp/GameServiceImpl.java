package com.svi.tictactoewebservice.services.imp;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.exceptions.SymbolAlreadyTakenException;
import com.svi.tictactoewebservice.models.Move;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.repositories.GameRepository;
import com.svi.tictactoewebservice.services.GameService;
import com.svi.tictactoewebservice.utils.FileUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@ApplicationScoped
public class GameServiceImpl implements GameService {

    private final Map<String, List<Move>> gameIdMoveCache = new ConcurrentHashMap<>();

    private final GameRepository gameRepository;

    @Inject
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void saveMove(SaveMoveRequest request) {

        Room room = FileUtil.parseGameId(request.getGameId());

        List<Move> gameMoves = gameIdMoveCache.computeIfAbsent(request.getGameId(), key -> new ArrayList<>());

        boolean positionTaken = gameMoves
                .stream()
                .anyMatch(move -> move.getPosition() == request.getLocation());

        if (positionTaken) {
            throw new SymbolAlreadyTakenException("Failed to place move, position is already taken");
        }

        try {
            gameRepository.savePlayerMoveOnTxt(
                    request.getPlayerId(),
                    room.getGameId()
            );

            gameRepository.saveGameMoveOnTxt(request);

            gameRepository.saveRoomOnTxt(
                    room.getRoomCode(),
                    room.getGameId()
            );

            gameMoves.add(new Move(
                    request.getPlayerId(),
                    request.getLocation()
            ));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<JsonObject> listGameMoves(String playerId) {
        if (FileUtil.gameNotExists(playerId)) {
            throw new RecordNotFoundException("Record not found");
        }

        return gameRepository.getGameMoves(playerId);
    }

    @Override
    public List<JsonObject> getGameIds() {

        Map<String, List<String>> gamesByRoom = FileUtil.getGamesByRoom();

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


}
