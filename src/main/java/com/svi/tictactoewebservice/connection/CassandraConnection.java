package com.svi.tictactoewebservice.connection;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.svi.tictactoewebservice.config.Config;

public class CassandraConnection implements AutoCloseable {

    private Cluster cluster;
    private Session session;

    public void initialize() {
        if (session != null) {
            return;
        }

        this.cluster = Cluster.builder()
                .addContactPoint(Config.get(Config.Key.CASSANDRA_IP.value()))
                .withPort(Integer.parseInt(Config.get(Config.Key.CASSANDRA_PORT.value())))
                .build();

        this.session = cluster.connect(Config.get(Config.Key.CASSANDRA_KEYSPACE.value()));
    }

    public Session getSession() {
        if (session == null) {
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

    @Override
    public void close() {
        destroy();
    }

}
