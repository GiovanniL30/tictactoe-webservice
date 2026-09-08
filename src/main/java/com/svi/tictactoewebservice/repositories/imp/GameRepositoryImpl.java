package com.svi.tictactoewebservice.repositories.imp;

import com.svi.tictactoewebservice.constants.ErrorMessages;
import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.models.GameMove;
import com.svi.tictactoewebservice.repositories.GameRepository;
import com.svi.tictactoewebservice.utils.FileUtil;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class GameRepositoryImpl implements GameRepository {

    @Override
    public List<GameMove> getGameMoves(String gameId) {
        Path gameFile = FileUtil.getGameRecordsPath().resolve(gameId + ".txt");

        try (Stream<String> lines = Files.lines(gameFile, StandardCharsets.UTF_8)) {
            return lines.filter(line -> !line.isEmpty())
                    .map(line -> line.split(",", 5))
                    .filter(parts -> parts.length == 5)
                    .map(parts -> new GameMove(
                            parts[0],
                            parts[1],
                            parts[2],
                            Integer.parseInt(parts[3]),
                            parts[4]))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(ErrorMessages.GAME_MOVES_RETRIEVAL_FAILED, e);
        }
    }

    @Override
    public void savePlayerGame(String playerId, String gameId) {
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
            throw new RuntimeException(ErrorMessages.PLAYER_GAME_SAVE_FAILED, e);
        }
    }

    @Override
    public void saveMove(SaveMoveRequest request) {
        Room room = FileUtil.parseGameId(request.getGameId());

        Path gameFile = FileUtil.getGameRecordsPath().resolve(room.getGameId() + ".txt");

        String record = String.join(",", room.getGameId(), request.getPlayerId(), request.getSymbol(), String.valueOf(request.getLocation()), request.getDatetime());

        try {
            Files.write(gameFile, (record + System.lineSeparator()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException(ErrorMessages.GAME_MOVE_SAVE_FAILED, e);
        }
    }

    @Override
    public void saveRoomGame(String roomCode, String gameId) {
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
            throw new RuntimeException(ErrorMessages.ROOM_GAME_SAVE_FAILED, e);
        }
    }

}
