package com.svi.tictactoewebservice.controllers;

import com.svi.tictactoewebservice.connection.CassandraConnection;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthController {

    private static final Logger LOGGER = Logger.getLogger(HealthController.class.getName());

    @GET
    public Response health() {
        try {
            CassandraConnection.getInstance()
                    .getSession()
                    .execute("SELECT release_version FROM system.local")
                    .one();

            return Response.ok(buildResponse("UP", "UP")).build();
        } catch (RuntimeException exception) {
            LOGGER.log(Level.WARNING, "Cassandra health check failed", exception);

            return Response
                    .status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity(buildResponse("DOWN", "DOWN"))
                    .build();
        }
    }

    private JsonObject buildResponse(String status, String databaseStatus) {
        return Json.createObjectBuilder()
                .add("status", status)
                .add("server", "UP")
                .add("database", databaseStatus)
                .build();
    }
}
