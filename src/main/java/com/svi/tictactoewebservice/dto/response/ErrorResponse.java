package com.svi.tictactoewebservice.dto.response;

import java.util.List;

public class ErrorResponse extends ApiResponse {

    private final List<String> errors;

    public ErrorResponse(String msg, List<String> errors) {
        super(msg);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}