package com.svi.tictactoewebservice.dao;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.svi.tictactoewebservice.config.Config;
import com.svi.tictactoewebservice.connection.CassandraConnection;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.utils.ValidationUtil;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class GamesByRoomDao {

    private static final String GAME_ID = "game_id";

    private final Session session;
    private final PreparedStatement insertRoom;
    private final PreparedStatement getRoomGames;

    public GamesByRoomDao() {
        this(CassandraConnection.getInstance().getSession());
    }

    private GamesByRoomDao(Session session) {
        this.session = ValidationUtil.requireNonNull(session, "session must not be null");

        String table = Config.get(Config.Key.GAMES_BY_ROOM_TABLE.value());

        this.insertRoom = session.prepare(String.format("INSERT INTO %s (room_code, game_id, created_at) VALUES (?, ?, ?)", table));
        this.getRoomGames = session.prepare(String.format("SELECT game_id FROM %s WHERE room_code = ?", table));
    }

    public void save(String roomCode, UUID gameId, Date createdAt) {
        ValidationUtil.requireText(roomCode, "roomCode");
        ValidationUtil.requireNonNull(gameId, "gameId must not be null");
        ValidationUtil.requireNonNull(createdAt, "createdAt must not be null");

        session.execute(insertRoom.bind(roomCode, gameId, createdAt));
    }

    public void save(String roomCode, String gameId) {
        save(roomCode, ValidationUtil.parseUuid(gameId, "gameId"), new Date());
    }

    public List<Room> getRoomGames(String roomCode) {
        ValidationUtil.requireText(roomCode, "roomCode");
        List<Room> games = new ArrayList<>();

        for (Row row : session.execute(getRoomGames.bind(roomCode))) {
            games.add(new Room(roomCode, row.getUUID(GAME_ID).toString()));
        }

        return games;
    }
}
