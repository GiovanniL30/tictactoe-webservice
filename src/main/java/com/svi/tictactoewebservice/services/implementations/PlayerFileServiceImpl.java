package com.svi.tictactoewebservice.services.implementations;

import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.repositories.PlayerFileRepository;
import com.svi.tictactoewebservice.services.interfaces.PlayerFileService;
import com.svi.tictactoewebservice.utils.FileUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import java.util.List;

@ApplicationScoped
public class PlayerFileServiceImpl implements PlayerFileService {

    private final PlayerFileRepository playerFileRepository;

    @Inject
    public PlayerFileServiceImpl(PlayerFileRepository playerFileRepository) {
        this.playerFileRepository = playerFileRepository;
    }

    @Override
    public List<JsonObject> listPlayerGames(String playerId) {
        if (FileUtil.playerNotExists(playerId)) {
            throw new RecordNotFoundException("Record not found");
        }

        return playerFileRepository.getPlayerGames(playerId);
    }

    @Override
    public List<JsonObject> getAllPlayers() {
        return playerFileRepository.listAllPlayers();
    }

}
