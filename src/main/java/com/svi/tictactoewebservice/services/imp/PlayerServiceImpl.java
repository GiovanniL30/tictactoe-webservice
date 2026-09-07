package com.svi.tictactoewebservice.services.imp;

import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.repositories.PlayerRepository;
import com.svi.tictactoewebservice.services.PlayerService;
import com.svi.tictactoewebservice.utils.FileUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.List;

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
            throw new RecordNotFoundException("Record not found");
        }

        return playerRepository.getPlayerGames(playerId);
    }

    @Override
    public List<JsonObject> getAllPlayers() {
        return playerRepository.listAllPlayers();
    }

}
