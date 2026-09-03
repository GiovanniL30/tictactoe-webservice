package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.utils.FileUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
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

    public List<JsonObject> getGameMoves(String gameId) {
        Path gameFile = FileUtil.getGameRecordsPath().resolve(gameId + ".txt");

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
        Path playerFile = FileUtil.getPlayerRecordsPath().resolve(playerId + ".txt");

        try {

            if (FileUtil.playerNotExists(playerId)) {
                Files.write(playerFile,
                        (gameId + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.WRITE);
                return;
            }

            if (FileUtil.recordDoesNotExist(playerFile, gameId)) {
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
        Room room = FileUtil.parseGameId(request.getGameId());

        Path gameFile = FileUtil.getGameRecordsPath().resolve(room.getGameId() + ".txt");

        String record = String.join(",", room.getGameId(), request.getPlayerId(), request.getSymbol(), String.valueOf(request.getLocation()), request.getDatetime());

        try {
            Files.write(gameFile, (record + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save game move record.", e);
        }
    }

    public void saveRoomOnTxt(String roomCode, String gameId) {
        Path roomFile = FileUtil.getRoomRecordsPath().resolve(roomCode + ".txt");

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

            if (FileUtil.recordDoesNotExist(roomFile, gameId)) {
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

}