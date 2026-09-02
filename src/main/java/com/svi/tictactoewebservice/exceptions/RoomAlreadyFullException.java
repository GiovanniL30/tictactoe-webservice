package com.svi.tictactoewebservice.exceptions;

import javax.ws.rs.core.Response;

public class RoomAlreadyFullException extends ApiException {

    public RoomAlreadyFullException(String message) {
        super(Response.Status.BAD_REQUEST, message);
    }

}
