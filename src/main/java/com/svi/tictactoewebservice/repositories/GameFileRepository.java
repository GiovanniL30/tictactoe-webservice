package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.config.Config;
import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class GameFileRepository {

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

    public List<JsonObject> listAllPlayers() {
        Map<String, List<String>> gamesByRoom = getGamesByRoom();

        try (Stream<Path> files = Files.list(getPlayerRecordsPath())) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(this::isTxtFile)
                    .map(playerFile -> buildPlayerRecord(playerFile, gamesByRoom))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve player records.", e);
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

    public void savePlayerMoveOnTxt(String playerId, String gameId) {
        Path playerFile = getPlayerRecordsPath().resolve(playerId + ".txt");

        try {

            if (playerNotExists(playerId)) {
                Files.write(playerFile,
                        (gameId + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE);
                return;
            }

            if (recordDoesNotExist(playerFile, gameId)) {
                Files.write(
                        playerFile,
                        (gameId + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.APPEND
                );
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to save player game record.", e);
        }
    }

    public void saveGameMoveOnTxt(SaveMoveRequest request) {
        Path gameFile = getGameRecordsPath().resolve(request.getGameId() + ".txt");

        String record = String.join(",", request.getGameId(), request.getPlayerId(), request.getSymbol(), String.valueOf(request.getLocation()), request.getDatetime());

        try {
            Files.write(gameFile, (record + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save game move record.", e);
        }
    }

    public void saveRoomOnTxt(String roomCode, String gameId) {
        Path roomFile = getRoomRecordsPath().resolve(roomCode + ".txt");

        try {
            if (!Files.exists(roomFile)) {
                Files.write(
                        roomFile,
                        (gameId + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE
                );
                return;
            }

            if (recordDoesNotExist(roomFile, gameId)) {
                Files.write(
                        roomFile,
                        (gameId + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.APPEND
                );
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to save room game record.", e);
        }
    }

    public Map<String, List<String>> getGamesByRoom() {
        try (Stream<Path> files = Files.list(getRoomRecordsPath())) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(this::isTxtFile)
                    .collect(Collectors.toMap(
                            this::getFileNameWithoutExtension,
                            this::readGameIds
                    ));

        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve room records.", e);
        }
    }

    private List<String> readGameIds(Path path) {
        try (Stream<String> lines = Files.lines(
                path,
                StandardCharsets.UTF_8
        )) {
            return lines
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read room record.",
                    e
            );
        }
    }

    private boolean isTxtFile(Path path) {
        return path.getFileName()
                .toString()
                .endsWith(".txt");
    }

    private String getFileNameWithoutExtension(Path path) {
        String fileName = path.getFileName().toString();

        return fileName.substring(
                0,
                fileName.length() - ".txt".length()
        );
    }

    private boolean recordDoesNotExist(Path file, String record) {
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            return lines.noneMatch(record::equals);
        } catch (IOException e) {
            throw new RuntimeException("Failed to check record.", e);
        }
    }

    private JsonObject buildPlayerRecord(Path playerFile, Map<String, List<String>> gamesByRoom) {
        String playerId = getFileNameWithoutExtension(playerFile);

        JsonArrayBuilder games = Json.createArrayBuilder();

        try (Stream<String> lines = Files.lines(playerFile, StandardCharsets.UTF_8)) {
            lines.map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .forEach(gameId -> {
                        String roomCode = findRoomCode(gameId, gamesByRoom);

                        games.add(
                                Json.createObjectBuilder()
                                        .add("gameid", gameId)
                                        .add("roomcode", roomCode)
                                        .build()
                        );
                    });

        } catch (IOException e) {
            throw new RuntimeException("Failed to read player record: " + playerId, e);
        }

        return Json.createObjectBuilder()
                .add("playerid", playerId)
                .add("games", games)
                .build();
    }

    private String findRoomCode(String gameId, Map<String, List<String>> gamesByRoom) {
        return gamesByRoom.entrySet()
                .stream()
                .filter(entry -> entry.getValue().contains(gameId))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    private Path getGameRecordsPath() {
        return (Path) servletContext.getAttribute(Config.Key.GAME_RECORDS_PATH.value());
    }

    private Path getPlayerRecordsPath() {
        return (Path) servletContext.getAttribute(Config.Key.PLAYER_RECORDS_PATH.value());
    }

    private Path getRoomRecordsPath() {
        return (Path) servletContext.getAttribute(Config.Key.ROOMS_RECORDS_PATH.value());
    }
}