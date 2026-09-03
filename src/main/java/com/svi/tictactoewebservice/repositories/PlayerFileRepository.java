package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.utils.FileUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class PlayerFileRepository {

    public List<JsonObject> getPlayerGames(String playerId) {
        Path playerFile = FileUtil.getPlayerRecordsPath().resolve(playerId + ".txt");

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
        Map<String, List<String>> gamesByRoom = FileUtil.getGamesByRoom();

        try (Stream<Path> files = Files.list(FileUtil.getPlayerRecordsPath())) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(FileUtil::isTxtFile)
                    .map(playerFile -> buildPlayerRecord(playerFile, gamesByRoom))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve player records.", e);
        }
    }

    private JsonObject buildPlayerRecord(Path playerFile, Map<String, List<String>> gamesByRoom) {
        String playerId = FileUtil.getFileNameWithoutExtension(playerFile);

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

}
