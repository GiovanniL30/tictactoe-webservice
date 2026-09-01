package com.svi.tictactoewebservice.config;

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

            Path recordsPath = projectPath.resolve("records");

            Files.createDirectories(recordsPath);

            event.getServletContext().setAttribute("recordsPath", recordsPath);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize records directory", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Application destroyed!");
    }
}