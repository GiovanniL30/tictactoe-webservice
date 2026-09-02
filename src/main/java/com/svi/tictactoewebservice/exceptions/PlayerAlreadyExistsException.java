package com.svi.tictactoewebservice.exceptions;

import javax.ws.rs.core.Response;

public class PlayerAlreadyExistsException extends ApiException {

    public PlayerAlreadyExistsException(String message) {
        super(Response.Status.BAD_REQUEST, message);
    }
}
