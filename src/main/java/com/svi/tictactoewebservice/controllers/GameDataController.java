package com.svi.tictactoewebservice.controllers;

import com.svi.tictactoewebservice.dto.request.IncreasePlayerScoreRequest;
import com.svi.tictactoewebservice.dto.request.PlayerRequest;
import com.svi.tictactoewebservice.dto.response.ApiResponse;
import com.svi.tictactoewebservice.dto.response.GetPlayersResponse;
import com.svi.tictactoewebservice.dto.response.IncreasePlayerScoreResponse;
import com.svi.tictactoewebservice.models.PlayerData;
import com.svi.tictactoewebservice.services.GameDataService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameDataController {

    @Inject
    private GameDataService gameDataService;

    @GET
    @Path("/v1/data/players/{roomCode}")
    public Response getPlayers(@PathParam("roomCode") String roomCode) {
        List<PlayerData> players = gameDataService.getPlayers(roomCode);

        return Response.ok(new GetPlayersResponse("Players found.", players)).build();
    }

    @POST
    @Path("/v1/data/players/{roomCode}")
    public Response addPlayers(
            @PathParam("roomCode") String roomCode,
            @Valid
            @Size(min = 2, max = 2, message = "Exactly 2 players are required.")
            List<@Valid PlayerRequest> players
    ) {
        if (players == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse("Request body is required."))
                    .build();
        }

        List<PlayerData> returnedPlayers = gameDataService.addPlayers(roomCode, players);

        return Response.ok(new GetPlayersResponse("Players added.", returnedPlayers)).build();
    }

    @PATCH
    @Path("/v1/data/player-score/increase")
    public Response increasePlayerScore(@Valid IncreasePlayerScoreRequest scoreRequest) {
        if (scoreRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse("Request body is required."))
                    .build();
        }

        PlayerData playerData = gameDataService.increasePlayerScore(scoreRequest);

        return Response.ok(new IncreasePlayerScoreResponse("Player score increased successfully.",playerData)).build();
    }


    @DELETE
    @Path("/v1/data/game/{roomCode}")
    public Response deleteGameData(@PathParam("roomCode") String roomCode) {
        List<PlayerData> deletedPlayers = gameDataService.deleteRoom(roomCode);
        return Response.ok(new GetPlayersResponse("Game data deleted.", deletedPlayers)).build();
    }
}