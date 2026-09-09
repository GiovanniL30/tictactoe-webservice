package com.svi.tictactoewebservice.services.imp;

import com.svi.tictactoewebservice.constants.ErrorMessages;
import com.svi.tictactoewebservice.dao.GamesByPlayerDao;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.services.PlayerService;
import com.svi.tictactoewebservice.models.Room;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class PlayerServiceImpl implements PlayerService {

    private final GamesByPlayerDao gamesByPlayerDao;

    @Inject
    public PlayerServiceImpl(GamesByPlayerDao gamesByPlayerDao) {
        this.gamesByPlayerDao = gamesByPlayerDao;
    }

    @Override
    public List<JsonObject> listPlayerGames(String playerId) {
        List<String> gameIds = gamesByPlayerDao.getPlayerGameIds(playerId);

        if (gameIds.isEmpty()) {
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND);
        }

        return gameIds.stream()
                .map(gameId -> Json.createObjectBuilder().add("gameId", gameId).build())
                .collect(Collectors.toList());
    }

    @Override
    public List<JsonObject> getAllPlayers() {
        return gamesByPlayerDao.getAllPlayerGames().entrySet().stream()
                .map(this::buildPlayerHistory)
                .collect(Collectors.toList());
    }

    private JsonObject buildPlayerHistory(Map.Entry<String, List<Room>> player) {
        JsonArrayBuilder games = Json.createArrayBuilder();
        player.getValue().forEach(game -> games.add(Json.createObjectBuilder()
                .add("gameId", game.getGameId())
                .add("roomCode", game.getRoomCode())));

        return Json.createObjectBuilder()
                .add("playerId", player.getKey())
                .add("games", games)
                .build();
    }

}
