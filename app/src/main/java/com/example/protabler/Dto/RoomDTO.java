package com.example.protabler.Dto;

public class RoomDTO {
    private int roomId;
    private String roomName;
    private int seatingCapacity;
    private String status;

    public RoomDTO(int roomId, String roomName, int seatingCapacity, String status) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.seatingCapacity = seatingCapacity;
        this.status = status;
    }

    public RoomDTO() {
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
}
