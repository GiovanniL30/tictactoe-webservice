package com.svi.tictactoewebservice.controllers;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.dto.response.ApiResponse;
import com.svi.tictactoewebservice.dto.response.ListGameResponse;
import com.svi.tictactoewebservice.services.GameService;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameController {

    @Inject
    private GameService gameService;

    @GET
    @Path("/v1/list-games/{playerId}")
    public Response listGames(@PathParam("playerId") String playerId) {
        List<JsonObject> playerGames = gameService.listPlayerGames(playerId);

        return Response.ok(new ListGameResponse(playerGames, "Records found")).build();
    }

    @GET
    @Path("/v1/games")
    public Response getAllGames() {
        List<JsonObject> gameIds = gameService.getGameIds();

        return Response.ok(new ListGameResponse(gameIds, "Records found")).build();
    }

    @GET
    @Path("/v1/game/{gameId}")
    public Response listGameMoves(@PathParam("gameId") String gameId) {
        List<JsonObject> gameMoves = gameService.listGameMoves(gameId);

        return Response.ok(new ListGameResponse(gameMoves, "Records found")).build();
    }

    @POST
    @Path("/v1/game/save")
    public Response saveGameData(@Valid SaveMoveRequest saveMoveRequest) {
        if (saveMoveRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponse("Request body is required.")).build();
        }

        gameService.saveMove(saveMoveRequest);

        return Response.ok(new ApiResponse("Record saved.")).build();
    }

}
