package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class GameRepository {

    private static final String RECORDS_PATH_ATTRIBUTE = "recordsPath";

    @Context
    private ServletContext servletContext;

    public boolean playerNotExists(String playerId) {
        Path playerFile = getRecordsPath().resolve(playerId + ".txt");
        return !Files.exists(playerFile);
    }

    public boolean gameNotExists(String gameId) {
        Path gameFile = getRecordsPath().resolve(gameId + ".txt");
        return !Files.exists(gameFile);
    }

    public List<JsonObject> getPlayerGames(String playerId) {
        Path playerFile = getRecordsPath().resolve(playerId + ".txt");

        try (Stream<String> lines = Files.lines(playerFile, StandardCharsets.UTF_8)) {
            return lines
                    .filter(line -> !line.isEmpty())
                    .map(gameId -> Json.createObjectBuilder()
                            .add("id", gameId)
                            .build())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve player games.", e);
        }
    }

    public List<JsonObject> getGameMoves(String gameId) {
        Path gameFile = getRecordsPath().resolve(gameId + ".txt");

        try (Stream<String> lines = Files.lines(gameFile, StandardCharsets.UTF_8)) {
            return lines
                    .filter(line -> !line.isEmpty())
                    .filter(line -> line.split(",").length == 5)
                    .map(details -> {
                        String[] parsed = details.split(",");
                        return Json.createObjectBuilder()
                                .add("id", parsed[0])
                                .add("playerid", parsed[1])
                                .add("symbol", parsed[2])
                                .add("location", parsed[3])
                                .add("datasaved", parsed[4])
                                .build();
                    } )
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve game moves.", e);
        }
    }

    public void savePlayerGame(String playerId, String gameId) {
        Path recordsPath = getRecordsPath();
        Path playerFile = recordsPath.resolve(playerId + ".txt");

        try {
            if (playerNotExists(playerId)) {
                Files.write(playerFile, (gameId + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
                return;
            }

            boolean gameAlreadyRecorded;

            try (Stream<String> lines = Files.lines(playerFile, StandardCharsets.UTF_8)) {

                gameAlreadyRecorded = lines.anyMatch(gameId::equals);
            }

            if (!gameAlreadyRecorded) {
                Files.write(playerFile, (gameId + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to save player game record.", e);
        }
    }

    public void saveGameMove(SaveMoveRequest request) {
        Path gameFile = getRecordsPath().resolve(request.getGameId() + ".txt");

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