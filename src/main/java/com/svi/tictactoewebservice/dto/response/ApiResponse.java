package com.svi.tictactoewebservice.dto.response;

public class ApiResponse {

    private String msg;

    public ApiResponse(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}