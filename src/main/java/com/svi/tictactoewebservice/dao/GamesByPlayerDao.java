package com.svi.tictactoewebservice.dao;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.svi.tictactoewebservice.config.Config;
import com.svi.tictactoewebservice.connection.CassandraConnection;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.utils.ValidationUtil;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class GamesByPlayerDao {

    private static final String PLAYER_ID = "player_id";
    private static final String GAME_ID = "game_id";
    private static final String ROOM_CODE = "room_code";

    private final Session session;
    private final PreparedStatement insertGame;
    private final PreparedStatement selectGamesByPlayer;
    private final PreparedStatement selectAllPlayerGames;

    public GamesByPlayerDao() {
        this(CassandraConnection.getInstance().getSession());
    }

    private GamesByPlayerDao(Session session) {
        this.session = ValidationUtil.requireNonNull(session, "session must not be null");

        String table = Config.get(Config.Key.GAMES_BY_PLAYER_TABLE.value());

        this.insertGame = session.prepare(String.format("INSERT INTO %s (player_id, game_id, created_at, room_code) VALUES (?, ?, ?, ?)", table));
        this.selectGamesByPlayer = session.prepare(String.format("SELECT game_id, room_code FROM %s WHERE player_id = ?", table));
        this.selectAllPlayerGames = session.prepare(String.format("SELECT player_id, game_id, room_code FROM %s", table));
    }

    public void save(String playerId, UUID gameId, Date createdAt, String roomCode) {
        ValidationUtil.requireText(playerId, "playerId");
        ValidationUtil.requireNonNull(gameId, "gameId must not be null");
        ValidationUtil.requireNonNull(createdAt, "createdAt must not be null");
        ValidationUtil.requireText(roomCode, "roomCode");

        session.execute(insertGame.bind(playerId, gameId, createdAt, roomCode));
    }

    public void save(String playerId, String gameId, String roomCode) {
        save(playerId, ValidationUtil.parseUuid(gameId, "gameId"), new Date(), roomCode);
    }

    public List<Room> getPlayerGames(String playerId) {
        ValidationUtil.requireText(playerId, "playerId");

        ResultSet resultSet = session.execute(selectGamesByPlayer.bind(playerId));
        List<Room> games = new ArrayList<>();

        for (Row row : resultSet) {
            UUID gameId = row.getUUID(GAME_ID);
            games.add(new Room(row.getString(ROOM_CODE), gameId.toString()));
        }

        return games;
    }

    public List<String> getPlayerGameIds(String playerId) {
        List<Room> games = getPlayerGames(playerId);
        List<String> gameIds = new ArrayList<>(games.size());

        for (Room game : games) {
            gameIds.add(game.getGameId());
        }

        return gameIds;
    }

    public Map<String, List<Room>> getAllPlayerGames() {
        Map<String, List<Room>> gamesByPlayer = new LinkedHashMap<>();

        for (Row row : session.execute(selectAllPlayerGames.bind())) {
            String playerId = row.getString(PLAYER_ID);
            Room game = new Room(
                    row.getString(ROOM_CODE),
                    row.getUUID(GAME_ID).toString()
            );
            gamesByPlayer.computeIfAbsent(playerId, key -> new ArrayList<>()).add(game);
        }

        return gamesByPlayer;
    }

}
