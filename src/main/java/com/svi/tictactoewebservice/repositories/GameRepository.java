package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

@ApplicationScoped
public class GameRepository {

    private static final String RECORDS_PATH_ATTRIBUTE = "recordsPath";

    @Context
    private ServletContext servletContext;

    public void savePlayerGame(String playerId, String gameId) {
        Path recordsPath = getRecordsPath();
        Path playerFile = recordsPath.resolve(playerId + ".txt");

        try {
            if (!Files.exists(playerFile)) {
                Files.write(playerFile, (gameId + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                return;
            }

            boolean gameExists;

            try (Stream<String> lines = Files.lines(playerFile, StandardCharsets.UTF_8)) {
                gameExists = lines.anyMatch(gameId::equals);
            }

            if (!gameExists) {
                Files.write(playerFile, (gameId + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to save player game record.", e);
        }
    }

    public void saveGameMove(SaveMoveRequest request) {
        Path recordsPath = getRecordsPath();
        Path gameFile = recordsPath.resolve(request.getGameId() + ".txt");

        String record = String.join(",", request.getGameId(), request.getPlayerId(), request.getSymbol(), String.valueOf(request.getLocation()), request.getDatetime());

        try {
            Files.write(gameFile, (record + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save game move record.", e);
        }
    }

    private Path getRecordsPath() {
        return (Path) servletContext.getAttribute(RECORDS_PATH_ATTRIBUTE);
    }
}