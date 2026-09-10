package com.svi.tictactoewebservice.services.imp;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.constants.ErrorMessages;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.exceptions.SymbolAlreadyTakenException;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.models.GameMove;
import com.svi.tictactoewebservice.repositories.GameRepository;
import com.svi.tictactoewebservice.services.GameService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Inject
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void saveMove(SaveMoveRequest request) {
        List<GameMove> gameMoves = gameRepository.getGameMoves(request.getGameId().toString());

        boolean positionTaken = gameMoves
                .stream()
                .anyMatch(move -> move.getLocation() == request.getLocation());

        if (positionTaken) {
            throw new SymbolAlreadyTakenException(ErrorMessages.POSITION_ALREADY_TAKEN);
        }

        int nextMoveNumber = gameMoves.stream()
                .mapToInt(GameMove::getMoveNumber)
                .max()
                .orElse(0) + 1;

        gameRepository.saveMove(request, nextMoveNumber);
    }


    @Override
    public List<GameMove> listGameMoves(String gameId) {
        List<GameMove> gameMoves = gameRepository.getGameMoves(gameId);

        if (gameMoves.isEmpty()) {
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND);
        }

        return gameMoves;
    }

    @Override
    public List<JsonObject> getGameIds() {

        Map<String, List<Room>> gamesByRoom = gameRepository.getAllRoomGames();

        return gamesByRoom.entrySet()
                .stream()
                .map(entry -> {

                    String roomCode = entry.getKey();
                    List<Room> games = entry.getValue();

                    JsonArrayBuilder gamesBuilder = Json.createArrayBuilder();

                    games.forEach(game -> gamesBuilder.add(
                            Json.createObjectBuilder()
                                    .add("gameId", game.getGameId())
                    ));

                    return Json.createObjectBuilder()
                            .add("roomCode", roomCode)
                            .add("gameCount", games.size())
                            .add("games", gamesBuilder.build())
                            .build();

                })
                .collect(Collectors.toList());
    }


}
