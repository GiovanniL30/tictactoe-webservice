package com.svi.tictactoewebservice.repositories.imp;

import com.svi.tictactoewebservice.constants.ErrorMessages;
import com.svi.tictactoewebservice.repositories.PlayerRepository;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.utils.FileUtil;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class PlayerRepositoryImpl implements PlayerRepository {

    @Override
    public List<String> getPlayerGames(String playerId) {
        Path playerFile = FileUtil.getPlayerRecordsPath().resolve(playerId + ".txt");

        try (Stream<String> lines = Files.lines(playerFile, StandardCharsets.UTF_8)) {
            return lines.map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(ErrorMessages.PLAYER_GAMES_RETRIEVAL_FAILED, e);
        }
    }

    @Override
    public Map<String, List<Room>> listAllPlayers() {
        Map<String, List<String>> gamesByRoom = FileUtil.getGamesByRoom();

        try (Stream<Path> files = Files.list(FileUtil.getPlayerRecordsPath())) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(FileUtil::isTxtFile)
                    .collect(Collectors.toMap(
                            FileUtil::getFileNameWithoutExtension,
                            playerFile -> buildPlayerGames(playerFile, gamesByRoom)
                    ));

        } catch (IOException e) {
            throw new RuntimeException(ErrorMessages.PLAYER_RECORDS_RETRIEVAL_FAILED, e);
        }
    }

    private List<Room> buildPlayerGames(Path playerFile, Map<String, List<String>> gamesByRoom) {
        String playerId = FileUtil.getFileNameWithoutExtension(playerFile);

        try (Stream<String> lines = Files.lines(playerFile, StandardCharsets.UTF_8)) {
            return lines.map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(gameId -> new Room(findRoomCode(gameId, gamesByRoom), gameId))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(ErrorMessages.format(ErrorMessages.PLAYER_RECORD_READ_FAILED, playerId), e);
        }

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
