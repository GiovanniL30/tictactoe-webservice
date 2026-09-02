package com.svi.tictactoewebservice.models;

public enum Symbol {
    X, O;

    public static Symbol fromString(String value) {
        return Symbol.valueOf(value.toUpperCase());
    }
}
