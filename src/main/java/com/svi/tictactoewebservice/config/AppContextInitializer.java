package com.svi.tictactoewebservice.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@WebListener
public class AppContextInitializer implements ServletContextListener {

    private static final String GAME_RECORDS_PATH = "gameRecordsPath";
    private static final String PLAYER_RECORDS_PATH = "playerRecordsPath";
    private static final String GAME_DATA_PATH = "gameDataPath";

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            String realPath = event.getServletContext().getRealPath("/");

            Path applicationPath = Paths.get(realPath).toAbsolutePath().normalize();

            Path projectPath = applicationPath.getParent().getParent();

            Path recordsPath = projectPath.resolve("records");

            Path gameRecordsPath = recordsPath.resolve("game");
            Path playerRecordsPath = recordsPath.resolve("players");
            Path gameDataPath = recordsPath.resolve("game-data.json");

            Files.createDirectories(gameRecordsPath);
            Files.createDirectories(playerRecordsPath);

            if (Files.notExists(gameDataPath)) {
                Files.write(gameDataPath, "{}".getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            }

            event.getServletContext().setAttribute(GAME_RECORDS_PATH, gameRecordsPath);

            event.getServletContext().setAttribute(PLAYER_RECORDS_PATH, playerRecordsPath);

            event.getServletContext().setAttribute(GAME_DATA_PATH, gameDataPath);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize records directories.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Application destroyed!");
    }
}