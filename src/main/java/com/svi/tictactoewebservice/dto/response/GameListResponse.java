package com.svi.tictactoewebservice.dto.response;

import java.util.List;

public class GameListResponse extends ApiResponse {

    private List<String> list;

    public GameListResponse(List<String> list, String msg) {
        super(msg);
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}