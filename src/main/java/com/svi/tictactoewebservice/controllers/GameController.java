package com.svi.tictactoewebservice.controllers;

import com.svi.tictactoewebservice.dto.request.SaveMoveRequest;
import com.svi.tictactoewebservice.dto.response.ApiResponse;
import com.svi.tictactoewebservice.services.GameService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/game")
public class GameController {

    @Inject
    private GameService gameService;

    @POST
    @Path("/save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveGame(@Valid SaveMoveRequest saveMoveRequest) {

        if (saveMoveRequest == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponse("Request body is required.")).build();
        }

        gameService.saveMove(saveMoveRequest);

        return Response.ok(new ApiResponse("Record saved.")).build();
    }

}
