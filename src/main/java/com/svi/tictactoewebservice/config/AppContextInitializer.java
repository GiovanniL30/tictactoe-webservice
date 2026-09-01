package com.svi.tictactoewebservice.config;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("Application initialized!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Application destroyed!");
    }
}