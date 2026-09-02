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
public class GameFileRepository {

    private static final String GAME_RECORDS_PATH_ATTRIBUTE = "gameRecordsPath";
    private static final String PLAYER_RECORDS_PATH_ATTRIBUTE = "playerRecordsPath";

    @Context
    private ServletContext servletContext;

    public boolean playerNotExists(String playerId) {
        Path playerFile = getPlayerRecordsPath().resolve(playerId + ".txt");

        return !Files.exists(playerFile);
    }

    public boolean gameNotExists(String gameId) {
        Path gameFile = getGameRecordsPath().resolve(gameId + ".txt");

        return !Files.exists(gameFile);
    }

    public List<JsonObject> getPlayerGames(String playerId) {
        Path playerFile = getPlayerRecordsPath().resolve(playerId + ".txt");

        try (Stream<String> lines = Files.lines(playerFile, StandardCharsets.UTF_8)) {
            return lines.filter(line -> !line.isEmpty())
                    .map(gameId -> Json.createObjectBuilder()
                            .add("id", gameId)
                            .build())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve player games.", e);
        }
    }

    public List<JsonObject> getGameMoves(String gameId) {
        Path gameFile = getGameRecordsPath().resolve(gameId + ".txt");

        try (Stream<String> lines = Files.lines(gameFile, StandardCharsets.UTF_8)) {
            return lines.filter(line -> !line.isEmpty())
                    .map(line -> line.split(",", 5))
                    .filter(parts -> parts.length == 5)
                    .map(parts ->
                            Json.createObjectBuilder()
                                    .add("id", parts[0])
                                    .add("playerid", parts[1])
                                    .add("symbol", parts[2])
                                    .add("location", parts[3])
                                    .add("datasaved", parts[4])
                                    .build())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve game moves.", e);
        }
    }

    public void savePlayerGame(String playerId, String gameId) {
        Path playerFile = getPlayerRecordsPath().resolve(playerId + ".txt");

        try {
            if (playerNotExists(playerId)) {
                Files.write(playerFile,
                        (gameId + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE);
                return;
            }

            boolean gameAlreadyRecorded;

            try (Stream<String> lines = Files.lines(playerFile, StandardCharsets.UTF_8)) {
                gameAlreadyRecorded = lines.anyMatch(gameId::equals);
            }

            if (!gameAlreadyRecorded) {
                Files.write(playerFile,
                        (gameId + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.APPEND);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to save player game record.", e);
        }
    }

    public void saveGameMove(SaveMoveRequest request) {
        Path gameFile = getGameRecordsPath().resolve(request.getGameId() + ".txt");

        String record = String.join(",", request.getGameId(), request.getPlayerId(), request.getSymbol(), String.valueOf(request.getLocation()), request.getDatetime());

        try {
            Files.write(gameFile, (record + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save game move record.", e);
        }
    }

    public List<String> getGameIds() {
        try (Stream<Path> files = Files.list(getGameRecordsPath())) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(this::isGameFile)
                    .map(this::getGameId)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve game records.", e);
        }
    }

    private String getGameId(Path path) {
        String fileName = path.getFileName().toString();

        return fileName.substring(
                0,
                fileName.length() - ".txt".length()
        );
    }

    private boolean isGameFile(Path path) {
        return path.getFileName()
                .toString()
                .endsWith(".txt");
    }

    private Path getGameRecordsPath() {
        return (Path) servletContext.getAttribute(GAME_RECORDS_PATH_ATTRIBUTE);
    }

    private Path getPlayerRecordsPath() {
        return (Path) servletContext.getAttribute(PLAYER_RECORDS_PATH_ATTRIBUTE);
    }
}