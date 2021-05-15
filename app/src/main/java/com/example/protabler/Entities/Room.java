package com.example.protabler.Entities;

import java.util.List;

public class Room {
    private int roomId;
    private String roomName;
    private int seatingCapacity;
    private String status;
    private List<Session> sessions;

    public Room(int roomId, String roomName, int seatingCapacity, String status, List<Session> sessions) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.seatingCapacity = seatingCapacity;
        this.status = status;
        this.sessions = sessions;
    }

    public Room() {
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
