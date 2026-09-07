package com.svi.tictactoewebservice.controllers;

import com.svi.tictactoewebservice.dto.response.GetHistoryPlayersResponse;
import com.svi.tictactoewebservice.dto.response.ListGameResponse;
import com.svi.tictactoewebservice.services.PlayerService;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/players")
public class PlayerController {

    private final PlayerService playerService;

    @Inject
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GET
    @Path("/{playerId}/games")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGames(@PathParam("playerId") String playerId) {
        List<JsonObject> playerGames = playerService.listPlayerGames(playerId);

        return Response.ok(new ListGameResponse(playerGames, "Records found")).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlayers() {
        List<JsonObject> players = playerService.getAllPlayers();

        return Response.ok(new GetHistoryPlayersResponse("Records found.", players)).build();
    }

}
