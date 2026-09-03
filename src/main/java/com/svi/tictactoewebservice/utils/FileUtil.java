package com.svi.tictactoewebservice.utils;

import com.svi.tictactoewebservice.models.Room;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FileUtil {

    private static boolean initialized = false;
    private static Path gameRecordsPath;
    private static Path playerRecordsPath;
    private static Path roomRecordsPath;

    private FileUtil() {
    }

    public static Room parseGameId(String requestGameId) {
        String[] gameIdParts = requestGameId.split("_", 2);

        String roomCode = gameIdParts[0];
        String gameId = gameIdParts[1];

        return new Room(roomCode, gameId);
    }

    public static void initialize(Path gameRecordsPath, Path playerRecordsPath, Path roomRecordsPath) {
        if (initialized) {
            return;
        }
        FileUtil.gameRecordsPath = gameRecordsPath;
        FileUtil.playerRecordsPath = playerRecordsPath;
        FileUtil.roomRecordsPath = roomRecordsPath;

        initialized = true;
    }

    public static Map<String, List<String>> getGamesByRoom() {
        try (Stream<Path> files = Files.list(FileUtil.getRoomRecordsPath())) {
            return files
                    .filter(Files::isRegularFile)
                    .filter(FileUtil::isTxtFile)
                    .collect(Collectors.toMap(
                            FileUtil::getFileNameWithoutExtension,
                            FileUtil::readGameIds
                    ));

        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve room records.", e);
        }
    }

    public static List<String> readGameIds(Path path) {
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

    public static boolean isTxtFile(Path path) {
        return path.getFileName()
                .toString()
                .endsWith(".txt");
    }

    public static String getFileNameWithoutExtension(Path path) {
        String fileName = path.getFileName().toString();

        return fileName.substring(
                0,
                fileName.length() - ".txt".length()
        );
    }

    public static boolean recordDoesNotExist(Path file, String record) {
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            return lines.noneMatch(record::equals);
        } catch (IOException e) {
            throw new RuntimeException("Failed to check record.", e);
        }
    }

    public static boolean gameNotExists(String gameId) {
        Path gameFile = FileUtil.getGameRecordsPath().resolve(gameId + ".txt");

        return !Files.exists(gameFile);
    }

    public static boolean playerNotExists(String playerId) {
        Path playerFile = getPlayerRecordsPath().resolve(playerId + ".txt");

        return !Files.exists(playerFile);
    }

    public static Path getGameRecordsPath() {
        return gameRecordsPath;
    }

    public static Path getPlayerRecordsPath() {
        return playerRecordsPath;
    }

    public static Path getRoomRecordsPath() {
        return roomRecordsPath;
    }

}
