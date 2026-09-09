package com.svi.tictactoewebservice.dao;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.svi.tictactoewebservice.config.Config;
import com.svi.tictactoewebservice.connection.CassandraConnection;
import com.svi.tictactoewebservice.models.GameMove;
import com.svi.tictactoewebservice.utils.DateTimeUtil;
import com.svi.tictactoewebservice.utils.ValidationUtil;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MovesByGameDao {

    private static final String GAME_ID = "game_id";
    private static final String MOVE_NUMBER = "move_number";
    private static final String LOCATION = "location";
    private static final String DATE_SAVED = "date_saved";
    private static final String PLAYER_ID = "player_id";
    private static final String SYMBOL = "symbol";

    private final Session session;
    private final PreparedStatement insertMove;
    private final PreparedStatement getGameMoves;

    public MovesByGameDao() {
        this(CassandraConnection.getInstance().getSession());
    }

    private MovesByGameDao(Session session) {
        this.session = ValidationUtil.requireNonNull(session, "session must not be null");

        String table = Config.get(Config.Key.MOVES_BY_GAME_TABLE.value());

        this.insertMove = session.prepare(String.format("INSERT INTO %s (game_id, move_number, location, date_saved, player_id, symbol) VALUES (?, ?, ?, ?, ?, ?)", table));
        this.getGameMoves = session.prepare(String.format("SELECT game_id, move_number, location, date_saved, player_id, symbol FROM %s WHERE game_id = ?", table));
    }

    public void save(UUID gameId, int moveNumber, int location, Date dateSaved, String playerId, String symbol) {
        ValidationUtil.requireNonNull(gameId, "gameId must not be null");
        ValidationUtil.requireNonNull(dateSaved, "dateSaved must not be null");
        ValidationUtil.requireText(playerId, "playerId");
        ValidationUtil.requireText(symbol, "symbol");

        if (moveNumber < 1 || moveNumber > 9) {
            throw new IllegalArgumentException("moveNumber must be between 1 and 9");
        }

        if (location < 0 || location > 8) {
            throw new IllegalArgumentException("location must be between 0 and 8");
        }

        session.execute(insertMove.bind(gameId, moveNumber, location, dateSaved, playerId, symbol));
    }

    public void save(String gameId, int moveNumber, int location, String dateSaved, String playerId, String symbol) {
        save(
                ValidationUtil.parseUuid(gameId, "gameId"),
                moveNumber,
                location,
                DateTimeUtil.parseDate(dateSaved),
                playerId,
                symbol
        );
    }

    public List<GameMove> getGameMoves(String gameId) {
        UUID parsedGameId = ValidationUtil.parseUuid(gameId, "gameId");
        List<GameMove> moves = new ArrayList<>();

        for (Row row : session.execute(getGameMoves.bind(parsedGameId))) {
            moves.add(new GameMove(
                    row.getUUID(GAME_ID).toString(),
                    row.getInt(MOVE_NUMBER),
                    row.getString(PLAYER_ID),
                    row.getString(SYMBOL),
                    row.getInt(LOCATION),
                    DateTimeUtil.format(row.getTimestamp(DATE_SAVED))
            ));
        }

        return moves;
    }
}
