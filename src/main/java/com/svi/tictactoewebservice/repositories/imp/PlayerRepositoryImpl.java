package com.svi.tictactoewebservice.repositories.imp;

import com.svi.tictactoewebservice.dao.GamesByPlayerDao;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.repositories.PlayerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class PlayerRepositoryImpl implements PlayerRepository {

    private final GamesByPlayerDao gamesByPlayerDao;

    @Inject
    public PlayerRepositoryImpl(GamesByPlayerDao gamesByPlayerDao) {
        this.gamesByPlayerDao = gamesByPlayerDao;
    }

    @Override
    public List<String> getPlayerGameIds(String playerId) {
        return gamesByPlayerDao.getPlayerGameIds(playerId);
    }

    @Override
    public Map<String, List<Room>> getAllPlayerGames() {
        return gamesByPlayerDao.getAllPlayerGames();
    }
}
