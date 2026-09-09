package com.svi.tictactoewebservice.controllers;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.dto.response.ApiResponse;
import com.svi.tictactoewebservice.dto.response.ListGameResponse;
import com.svi.tictactoewebservice.models.GameMove;
import com.svi.tictactoewebservice.services.GameService;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/games")
public class GameController {

    private final GameService gameService;

    @Inject
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGames() {
        List<JsonObject> gameIds = gameService.getGameIds();

        return Response.ok(new ListGameResponse<>(gameIds, "Records found")).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveGameData(@Valid SaveMoveRequest saveMoveRequest) {
        gameService.saveMove(saveMoveRequest);

        return Response.status(Response.Status.CREATED)
                .entity(new ApiResponse("Record saved."))
                .build();
    }

    @GET
    @Path("/{gameId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGameMoves(@PathParam("gameId") String gameId) {
        List<GameMove> gameMoves = gameService.listGameMoves(gameId);

        return Response.ok(new ListGameResponse<>(gameMoves, "Records found")).build();
    }

}
