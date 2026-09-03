package com.svi.tictactoewebservice.dto.response;

import com.svi.tictactoewebservice.models.Room;

public class GameKeyResponse extends ApiResponse{

    private final Room room;

    public GameKeyResponse(String msg, Room room) {
        super(msg);
        this.room = room;
    }

    public Room getGameKey() {
        return room;
    }
}
