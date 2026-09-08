package com.svi.tictactoewebservice.dto.response;

import java.util.List;

public class ListGameResponse<T> extends ApiResponse {

    private List<T> list;

    public ListGameResponse(List<T> list, String msg) {
        super(msg);
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
