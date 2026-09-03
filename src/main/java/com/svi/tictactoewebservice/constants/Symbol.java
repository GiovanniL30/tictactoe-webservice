package com.svi.tictactoewebservice.constants;

public enum Symbol {
    X, O;

    public static Symbol fromString(String value) {
        return Symbol.valueOf(value.toUpperCase());
    }
}
