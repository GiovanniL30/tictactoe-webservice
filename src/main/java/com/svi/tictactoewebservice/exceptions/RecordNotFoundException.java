package com.svi.tictactoewebservice.exceptions;

import javax.ws.rs.core.Response;

public class RecordNotFoundException extends ApiException {

    public RecordNotFoundException(String message) {
        super(Response.Status.NOT_FOUND, message);
    }
}