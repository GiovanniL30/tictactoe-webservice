package com.svi.tictactoewebservice.config;

import com.svi.tictactoewebservice.utils.FileUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebListener
public class AppContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            String realPath = event.getServletContext().getRealPath("/");

            Path applicationPath = Paths.get(realPath).toAbsolutePath().normalize();

            Path projectPath = applicationPath.getParent().getParent();

            Path gameRecordsPath = projectPath.resolve(Config.get(Config.Key.GAME_RECORDS_PATH.value()));

            Path playerRecordsPath = projectPath.resolve(Config.get(Config.Key.PLAYER_RECORDS_PATH.value()));

            Path roomsRecordsPath = projectPath.resolve(Config.get(Config.Key.ROOMS_RECORDS_PATH.value()));

            Files.createDirectories(gameRecordsPath);
            Files.createDirectories(playerRecordsPath);
            Files.createDirectories(roomsRecordsPath);

            FileUtil.initialize(gameRecordsPath, playerRecordsPath, roomsRecordsPath);

            event.getServletContext().setAttribute(Config.Key.GAME_RECORDS_PATH.value(), gameRecordsPath);

            event.getServletContext().setAttribute(Config.Key.PLAYER_RECORDS_PATH.value(), playerRecordsPath);

            event.getServletContext().setAttribute(Config.Key.ROOMS_RECORDS_PATH.value(), roomsRecordsPath);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize records directories.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Application destroyed!");
    }
}