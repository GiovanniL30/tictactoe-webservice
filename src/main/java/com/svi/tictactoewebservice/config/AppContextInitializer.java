package com.svi.tictactoewebservice.config;

import com.svi.tictactoewebservice.connection.CassandraConnection;
import com.svi.tictactoewebservice.constants.ErrorMessages;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

@WebListener
public class AppContextInitializer implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(AppContextInitializer.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            LOGGER.info("Application started");
            CassandraConnection.getInstance().initialize();
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.CASSANDRA_INITIALIZATION_FAILED, e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        LOGGER.info("Application destroyed.");
        CassandraConnection.getInstance().destroy();
    }
}
