package com.svi.tictactoewebservice.services.imp;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.constants.ErrorMessages;
import com.svi.tictactoewebservice.dao.GamesByPlayerDao;
import com.svi.tictactoewebservice.dao.GamesByRoomDao;
import com.svi.tictactoewebservice.dao.MovesByGameDao;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.exceptions.SymbolAlreadyTakenException;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.models.GameMove;
import com.svi.tictactoewebservice.services.GameService;
import com.svi.tictactoewebservice.utils.GameIdUtil;

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

    private final GamesByPlayerDao gamesByPlayerDao;
    private final GamesByRoomDao gamesByRoomDao;
    private final MovesByGameDao movesByGameDao;

    @Inject
    public GameServiceImpl(
            GamesByPlayerDao gamesByPlayerDao,
            GamesByRoomDao gamesByRoomDao,
            MovesByGameDao movesByGameDao
    ) {
        this.gamesByPlayerDao = gamesByPlayerDao;
        this.gamesByRoomDao = gamesByRoomDao;
        this.movesByGameDao = movesByGameDao;
    }

    @Override
    public void saveMove(SaveMoveRequest request) {

        Room room = GameIdUtil.parse(request.getGameId());

        List<GameMove> gameMoves = movesByGameDao.getGameMoves(room.getGameId());

        boolean positionTaken = gameMoves
                .stream()
                .anyMatch(move -> move.getLocation() == request.getLocation());

        if (positionTaken) {
            throw new SymbolAlreadyTakenException(ErrorMessages.POSITION_ALREADY_TAKEN);
        }

        movesByGameDao.save(
                room.getGameId(),
                request.getLocation(),
                request.getDatetime(),
                request.getPlayerId(),
                request.getSymbol()
        );
        gamesByRoomDao.save(room.getRoomCode(), room.getGameId());
        gamesByPlayerDao.save(request.getPlayerId(), room.getGameId(), room.getRoomCode());

    }


    @Override
    public List<GameMove> listGameMoves(String gameId) {
        List<GameMove> gameMoves = movesByGameDao.getGameMoves(gameId);

        if (gameMoves.isEmpty()) {
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND);
        }

        return gameMoves;
    }

    @Override
    public List<JsonObject> getGameIds() {

        Map<String, List<Room>> gamesByRoom = gamesByRoomDao.getAllRoomGames();

        return gamesByRoom.entrySet()
                .stream()
                .map(entry -> {

                    String roomCode = entry.getKey();
                    List<Room> games = entry.getValue();

                    JsonArrayBuilder gamesBuilder = Json.createArrayBuilder();

                    games.forEach(game -> gamesBuilder.add(
                            Json.createObjectBuilder()
                                    .add("gameid", game.getGameId())
                    ));

                    return Json.createObjectBuilder()
                            .add("roomcode", roomCode)
                            .add("gamecount", games.size())
                            .add("games", gamesBuilder.build())
                            .build();

                })
                .collect(Collectors.toList());
    }


}