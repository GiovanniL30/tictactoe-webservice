package com.svi.tictactoewebservice.services.imp;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.constants.ErrorMessages;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.exceptions.SymbolAlreadyTakenException;
import com.svi.tictactoewebservice.models.Move;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.models.GameMove;
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
            throw new SymbolAlreadyTakenException(ErrorMessages.POSITION_ALREADY_TAKEN);
        }

        gameRepository.savePlayerGame(
                request.getPlayerId(),
                room.getGameId()
        );

        gameRepository.saveMove(request);

        gameRepository.saveRoomGame(
                room.getRoomCode(),
                room.getGameId()
        );

        gameMoves.add(new Move(
                request.getPlayerId(),
                request.getLocation()
        ));

    }


    @Override
    public List<GameMove> listGameMoves(String gameId) {
        if (FileUtil.gameNotExists(gameId)) {
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND);
        }

        return gameRepository.getGameMoves(gameId);
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
