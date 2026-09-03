package com.svi.tictactoewebservice.controllers;

import com.svi.tictactoewebservice.dto.response.ListGameResponse;
import com.svi.tictactoewebservice.services.interfaces.GameFileService;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/player")
public class PlayerFileController {

    private final GameFileService gameFileService;

    @Inject
    public PlayerFileController(GameFileService gameFileService) {
        this.gameFileService = gameFileService;
    }

    @GET
    @Path("/{playerId}/games")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response listGames(@PathParam("playerId") String playerId) {
        List<JsonObject> playerGames = gameFileService.listPlayerGames(playerId);

        return Response.ok(new ListGameResponse(playerGames, "Records found")).build();
    }

}
