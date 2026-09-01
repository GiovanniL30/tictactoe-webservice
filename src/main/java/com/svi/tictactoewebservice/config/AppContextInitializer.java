package com.svi.tictactoewebservice.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebListener
public class AppContextInitializer implements ServletContextListener {

    private static final String GAME_RECORDS_PATH = "gameRecordsPath";
    private static final String PLAYER_RECORDS_PATH = "playerRecordsPath";

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            String realPath = event.getServletContext().getRealPath("/");

            Path applicationPath = Paths.get(realPath).toAbsolutePath().normalize();

            Path projectPath = applicationPath.getParent().getParent();

            Path recordsPath = projectPath.resolve("records");

            Path gameRecordsPath = recordsPath.resolve("game");
            Path playerRecordsPath = recordsPath.resolve("players");

            Files.createDirectories(gameRecordsPath);
            Files.createDirectories(playerRecordsPath);

            event.getServletContext().setAttribute(GAME_RECORDS_PATH, gameRecordsPath);

            event.getServletContext().setAttribute(PLAYER_RECORDS_PATH, playerRecordsPath);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize records directories", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Application destroyed!");
    }
}