package com.svi.tictactoewebservice.exceptions;

import javax.ws.rs.core.Response;

public class SymbolOrderException extends ApiException {
    public SymbolOrderException(String message) {
        super(Response.Status.BAD_REQUEST, message);
    }
}
