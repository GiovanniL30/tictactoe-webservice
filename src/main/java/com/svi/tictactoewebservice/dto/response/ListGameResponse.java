package com.svi.tictactoewebservice.dto.response;

import javax.json.JsonObject;
import java.util.List;

public class ListGameResponse extends ApiResponse {

    private List<JsonObject> list;

    public ListGameResponse(List<JsonObject> list, String msg) {
        super(msg);
        this.list = list;
    }

    public List<JsonObject> getList() {
        return list;
    }

    public void setList(List<JsonObject> list) {
        this.list = list;
    }
}