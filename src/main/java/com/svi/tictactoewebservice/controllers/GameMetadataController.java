package com.svi.tictactoewebservice.controllers;

import com.svi.tictactoewebservice.dto.request.IncreasePlayerScoreRequest;
import com.svi.tictactoewebservice.dto.request.PlayerRequest;
import com.svi.tictactoewebservice.dto.response.ApiResponse;
import com.svi.tictactoewebservice.dto.response.GetPlayersResponse;
import com.svi.tictactoewebservice.dto.response.IncreasePlayerScoreResponse;
import com.svi.tictactoewebservice.models.PlayerData;
import com.svi.tictactoewebservice.services.GameMetadataService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameMetadataController {

    @Inject
    private GameMetadataService gameMetadataService;

    @GET
    @Path("/v1/data/players/{roomCode}")
    public Response getPlayers(@PathParam("roomCode") String roomCode) {
        List<PlayerData> players = gameMetadataService.getPlayers(roomCode);

        return Response.ok(new GetPlayersResponse("Players found.", players)).build();
    }

    @POST
    @Path("/v1/data/player/{roomCode}")
    public Response addPlayer(
            @PathParam("roomCode") String roomCode,
            @Valid PlayerRequest player
    ) {
        if (player == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse("Request body is required."))
                    .build();
        }

        PlayerData addedPlayer = gameMetadataService.addPlayer(roomCode, player);

        return Response.ok(new GetPlayersResponse("Player added.", Collections.singletonList(addedPlayer))).build();
    }

    @PATCH
    @Path("/v1/data/player-score/increase")
    public Response increasePlayerScore(@Valid IncreasePlayerScoreRequest scoreRequest) {
        if (scoreRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiResponse("Request body is required."))
                    .build();
        }

        PlayerData playerData = gameMetadataService.increasePlayerScore(scoreRequest);

        return Response.ok(new IncreasePlayerScoreResponse("Player score increased successfully.", playerData)).build();
    }


    @DELETE
    @Path("/v1/data/game/{roomCode}")
    public Response deleteGameData(@PathParam("roomCode") String roomCode) {
        List<PlayerData> deletedPlayers = gameMetadataService.deleteRoom(roomCode);
        return Response.ok(new GetPlayersResponse("Game data deleted.", deletedPlayers)).build();
    }
}