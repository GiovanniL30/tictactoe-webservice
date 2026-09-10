package com.svi.tictactoewebservice.repositories.imp;

import com.svi.tictactoewebservice.dao.GamesByPlayerDao;
import com.svi.tictactoewebservice.dao.GamesByRoomDao;
import com.svi.tictactoewebservice.dao.MovesByGameDao;
import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.models.GameMove;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.repositories.GameRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class GameRepositoryImpl implements GameRepository {

    private final GamesByPlayerDao gamesByPlayerDao;
    private final GamesByRoomDao gamesByRoomDao;
    private final MovesByGameDao movesByGameDao;

    @Inject
    public GameRepositoryImpl(
            GamesByPlayerDao gamesByPlayerDao,
            GamesByRoomDao gamesByRoomDao,
            MovesByGameDao movesByGameDao
    ) {
        this.gamesByPlayerDao = gamesByPlayerDao;
        this.gamesByRoomDao = gamesByRoomDao;
        this.movesByGameDao = movesByGameDao;
    }

    @Override
    public void saveMove(SaveMoveRequest request, int moveNumber) {
        String gameId = request.getGameId().toString();

        movesByGameDao.save(
                gameId,
                moveNumber,
                request.getLocation(),
                request.getDatetime(),
                request.getPlayerId(),
                request.getSymbol()
        );
        gamesByRoomDao.save(request.getRoomCode(), gameId);
        gamesByPlayerDao.save(request.getPlayerId(), gameId, request.getRoomCode());
    }

    @Override
    public List<GameMove> getGameMoves(String gameId) {
        return movesByGameDao.getGameMoves(gameId);
    }

    @Override
    public Map<String, List<Room>> getAllRoomGames() {
        return gamesByRoomDao.getAllRoomGames();
    }
}
