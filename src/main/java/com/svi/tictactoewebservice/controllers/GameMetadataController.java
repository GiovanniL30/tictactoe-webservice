package com.svi.tictactoewebservice.controllers;

import com.svi.tictactoewebservice.dto.request.IncreasePlayerScoreRequest;
import com.svi.tictactoewebservice.dto.request.PlayerRequest;
import com.svi.tictactoewebservice.dto.response.ApiResponse;
import com.svi.tictactoewebservice.dto.response.GameKeyResponse;
import com.svi.tictactoewebservice.dto.response.GetPlayersResponse;
import com.svi.tictactoewebservice.dto.response.IncreasePlayerScoreResponse;
import com.svi.tictactoewebservice.models.Room;
import com.svi.tictactoewebservice.models.PlayerData;
import com.svi.tictactoewebservice.services.GameMetadataService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/data")
public class GameMetadataController {

    private final GameMetadataService gameMetadataService;

    @Inject
    public GameMetadataController(GameMetadataService gameMetadataService) {
        this.gameMetadataService = gameMetadataService;
    }

    @GET
    @Path("/players/{roomCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayers(@PathParam("roomCode") String roomCode) {
        List<PlayerData> players = gameMetadataService.getPlayers(roomCode);

        return Response.ok(new GetPlayersResponse("Players found.", players)).build();
    }

    @GET
    @Path("/game-key/generate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateRoomKeys() {
        Room room = gameMetadataService.generateRoomKeys();
        return Response.status(Response.Status.CREATED)
                .entity(new GameKeyResponse("Generated Room Keys.", room))
                .build();
    }

    @GET
    @Path("/game-key/{roomCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomUUID(@PathParam("roomCode") String roomCode) {
        String gameRoomUUID = gameMetadataService.getRoomUUID(roomCode);
        return Response.ok(new GameKeyResponse("Room Keys", new Room(roomCode, gameRoomUUID))).build();
    }


    @POST
    @Path("/player/{roomCode}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlayer(@PathParam("roomCode") String roomCode, @Valid PlayerRequest player) {
        PlayerData addedPlayer = gameMetadataService.addPlayer(roomCode, player);

        return Response.status(Response.Status.CREATED)
                .entity(new GetPlayersResponse("Player added.", Collections.singletonList(addedPlayer)))
                .build();
    }

    @PATCH
    @Path("/player-score/increase")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response increasePlayerScore(@Valid IncreasePlayerScoreRequest scoreRequest) {
        PlayerData playerData = gameMetadataService.increasePlayerScore(scoreRequest);

        return Response.ok(new IncreasePlayerScoreResponse("Player score increased successfully.", playerData)).build();
    }


    @DELETE
    @Path("/game/{roomCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGameData(@PathParam("roomCode") String roomCode) {
        gameMetadataService.deleteRoom(roomCode);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/game-key/regenerate/{roomCode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response regenerateGameUUID(@PathParam("roomCode") String roomCode) {
        String newGameUUID = gameMetadataService.removeGameUUID(roomCode);
        return Response.ok(new GameKeyResponse("Game UUID regenerated.", new Room(roomCode, newGameUUID))).build();
    }
}
