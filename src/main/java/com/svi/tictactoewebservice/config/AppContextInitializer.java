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
    private static final String ROOMS_RECORDS_PATH = "roomsRecordsPath";

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            String realPath = event.getServletContext().getRealPath("/");

            Path applicationPath = Paths.get(realPath).toAbsolutePath().normalize();

            Path projectPath = applicationPath.getParent().getParent();

            Path recordsPath = projectPath.resolve("records");

            Path gameRecordsPath = recordsPath.resolve("games");
            Path playerRecordsPath = recordsPath.resolve("players");
            Path roomsRecordsPath = recordsPath.resolve("rooms");

            Files.createDirectories(gameRecordsPath);
            Files.createDirectories(playerRecordsPath);
            Files.createDirectories(roomsRecordsPath);

            event.getServletContext().setAttribute(GAME_RECORDS_PATH, gameRecordsPath);

            event.getServletContext().setAttribute(PLAYER_RECORDS_PATH, playerRecordsPath);

            event.getServletContext().setAttribute(ROOMS_RECORDS_PATH, roomsRecordsPath);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize records directories.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Application destroyed!");
    }
}