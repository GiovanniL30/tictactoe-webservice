package com.svi.tictactoewebservice.repositories;

import com.svi.tictactoewebservice.exceptions.RecordNotFoundException;
import com.svi.tictactoewebservice.exceptions.RoomAlreadyFullException;
import com.svi.tictactoewebservice.models.PlayerData;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class GameDataRepository {

    // Room code : Players
    private final Map<String, List<PlayerData>> rooms = new HashMap<>();

    public List<PlayerData> addPlayers(String roomCode, List<PlayerData> players) {
        if (!roomNotExists(roomCode) && rooms.get(roomCode).size() >= 2) {
            throw new RoomAlreadyFullException(
                    "Room already contains 2 players. Cannot add more players."
            );
        }

        rooms.put(roomCode, players);
        return players;
    }

    public List<PlayerData> getPlayers(String roomCode) {
        return rooms.get(roomCode);
    }

    public List<PlayerData> removeRoom(String roomCode) {
        return rooms.remove(roomCode);
    }

    public PlayerData increasePlayerScore(String roomCode, String playerId, int count) {
        if(roomNotExists(roomCode)) {
            throw  new RecordNotFoundException("Room Code does not exists.");
        }

        List<PlayerData> players = rooms.get(roomCode);

        PlayerData player = players.stream()
                .filter(p -> p.getUuid().equals(playerId))
                .findFirst()
                .orElseThrow(() ->
                        new RecordNotFoundException("Player does not exist in the room."));

        player.setScore(player.getScore() + count);

        return player;
    }

    public boolean roomNotExists(String roomCode) {
        return roomCode == null
                || roomCode.isEmpty()
                || !rooms.containsKey(roomCode);
    }
}