package com.svi.tictactoewebservice.services.imp;

import com.svi.tictactoewebservice.constants.ErrorMessages;
import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.repositories.PlayerRepository;
import com.svi.tictactoewebservice.services.PlayerService;
import com.svi.tictactoewebservice.utils.FileUtil;
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

    private final PlayerRepository playerRepository;

    @Inject
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<JsonObject> listPlayerGames(String playerId) {
        if (FileUtil.playerNotExists(playerId)) {
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND);
        }

        return playerRepository.getPlayerGames(playerId).stream()
                .map(gameId -> Json.createObjectBuilder().add("id", gameId).build())
                .collect(Collectors.toList());
    }

    @Override
    public List<JsonObject> getAllPlayers() {
        return playerRepository.listAllPlayers().entrySet().stream()
                .map(this::buildPlayerHistory)
                .collect(Collectors.toList());
    }

    private JsonObject buildPlayerHistory(Map.Entry<String, List<Room>> player) {
        JsonArrayBuilder games = Json.createArrayBuilder();
        player.getValue().forEach(game -> games.add(Json.createObjectBuilder()
                .add("gameid", game.getGameId())
                .add("roomcode", game.getRoomCode())));

        return Json.createObjectBuilder()
                .add("playerid", player.getKey())
                .add("games", games)
                .build();
    }

}
