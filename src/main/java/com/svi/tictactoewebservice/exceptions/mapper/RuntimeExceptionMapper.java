package com.svi.tictactoewebservice.exceptions.mapper;

import com.svi.tictactoewebservice.constants.ErrorMessages;
import com.svi.tictactoewebservice.dto.response.ApiResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    private static final Logger LOGGER = Logger.getLogger(RuntimeExceptionMapper.class.getName());

    @Override
    public Response toResponse(RuntimeException exception) {
        LOGGER.log(Level.SEVERE, "Unexpected server error", exception);

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ApiResponse(ErrorMessages.UNEXPECTED_SERVER_ERROR))
                .build();
    }
}
