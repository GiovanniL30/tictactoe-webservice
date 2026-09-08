package com.svi.tictactoewebservice.exceptions.mapper;

import com.svi.tictactoewebservice.constants.ErrorMessages;
import com.svi.tictactoewebservice.dto.response.ApiResponse;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        exception.printStackTrace();

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ApiResponse(ErrorMessages.format(ErrorMessages.UNEXPECTED_SERVER_ERROR, exception.getMessage())))
                .build();
    }
}
