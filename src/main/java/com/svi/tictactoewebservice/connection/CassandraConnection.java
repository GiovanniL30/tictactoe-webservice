package com.svi.tictactoewebservice.connection;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.svi.tictactoewebservice.config.Config;

public class CassandraConnection {

    private Cluster cluster;
    private Session session;

    private CassandraConnection() {
    }

    public static CassandraConnection getInstance() {
        return Holder.INSTANCE;
    }

    public void initialize() {
        if (session != null && !session.isClosed()) {
            return;
        }

        this.cluster = Cluster.builder()
                .addContactPoint(Config.get(Config.Key.CASSANDRA_IP.value()))
                .withPort(Integer.parseInt(
                        Config.get(Config.Key.CASSANDRA_PORT.value())
                ))
                .build();

        this.session = cluster.connect(
                Config.get(Config.Key.CASSANDRA_KEYSPACE.value())
        );
    }

    public Session getSession() {
        if (session == null || session.isClosed()) {
            initialize();
        }

        return session;
    }

    public void destroy() {
        if (session != null && !session.isClosed()) {
            session.close();
        }

        if (cluster != null && !cluster.isClosed()) {
            cluster.close();
        }
    }

    private static class Holder {
        private static final CassandraConnection INSTANCE = new CassandraConnection();
    }
}