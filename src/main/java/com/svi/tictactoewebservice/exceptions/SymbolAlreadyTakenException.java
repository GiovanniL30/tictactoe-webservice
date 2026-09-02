package com.svi.tictactoewebservice.exceptions;

import javax.ws.rs.core.Response;

public class SymbolAlreadyTakenException extends ApiException {
    public SymbolAlreadyTakenException(String message) {
        super(Response.Status.BAD_REQUEST, message);
    }
}
