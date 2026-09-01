package com.svi.tictactoewebservice.controllers;

import com.svi.tictactoewebservice.dto.request.PlayerRequest;
import com.svi.tictactoewebservice.dto.request.RoundRequest;
import com.svi.tictactoewebservice.dto.request.ScoreRequest;
import com.svi.tictactoewebservice.dto.response.ApiResponse;
import com.svi.tictactoewebservice.services.GameDataService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameDataController {

    @Inject
    private GameDataService gameDataService;

    @GET
    @Path("/v1/data/{gameId}")
    public Response getGameData(@PathParam("gameId") String gameId) {
        return null;
    }

    @POST
    @Path("/v1/data/{gameId}/player")
    public Response addPlayer(@PathParam("gameId") String gameId, @Valid PlayerRequest playerRequest) {
        if (playerRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponse("Request body is required.")).build();
        }

        return Response.ok(new ApiResponse("Player added.")).build();
    }

    @PATCH
    @Path("/v1/data/{gameId}/score")
    public Response updateScore(@PathParam("gameId") String gameId, @Valid ScoreRequest scoreRequest) {
        if (scoreRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponse("Request body is required.")).build();
        }

        return Response.ok(new ApiResponse("Score updated.")).build();
    }

    @PATCH
    @Path("/v1/data/{gameId}/round")
    public Response updateRound(@PathParam("gameId") String gameId, @Valid RoundRequest roundRequest) {
        if (roundRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponse("Request body is required.")).build();
        }

        return Response.ok(new ApiResponse("Round updated.")).build();
    }


    @DELETE
    @Path("/v1/data/{gameId}")
    public Response deleteGameData(@PathParam("gameId") String gameId) {
        return Response.ok(new ApiResponse("Game data deleted.")).build();
    }
}