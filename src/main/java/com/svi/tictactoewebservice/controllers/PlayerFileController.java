package com.svi.tictactoewebservice.controllers;

import com.svi.tictactoewebservice.dto.response.GetHistoryPlayersResponse;
import com.svi.tictactoewebservice.dto.response.ListGameResponse;
import com.svi.tictactoewebservice.services.interfaces.PlayerFileService;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/player")
public class PlayerFileController {

    private final PlayerFileService playerFileService;

    @Inject
    public PlayerFileController(PlayerFileService playerFileService) {
        this.playerFileService = playerFileService;
    }

    @GET
    @Path("/{playerId}/games")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response listGames(@PathParam("playerId") String playerId) {
        List<JsonObject> playerGames = playerFileService.listPlayerGames(playerId);

        return Response.ok(new ListGameResponse(playerGames, "Records found")).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllPlayers() {
        List<JsonObject> players = playerFileService.getAllPlayers();

        return Response.ok(new GetHistoryPlayersResponse("Records found.", players)).build();
    }

}
