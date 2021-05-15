package com.example.protabler.Dto;

public class SessionDTO {
    private int sessionId;
    private String moduleTitle;
    private String batchTitle;
    private String roomName;
    private String lecturerName;
    private String day;
    private String lectureTime;
    private String lectureType;

    public SessionDTO(int sessionId, String moduleTitle, String batchTitle, String roomName, String lecturerName, String day, String lectureTime, String lectureType) {
        this.sessionId = sessionId;
        this.moduleTitle = moduleTitle;
        this.batchTitle = batchTitle;
        this.roomName = roomName;
        this.lecturerName = lecturerName;
        this.day = day;
        this.lectureTime = lectureTime;
        this.lectureType = lectureType;
    }

    public SessionDTO() {
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String getBatchTitle() {
        return batchTitle;
    }

    public void setBatchTitle(String batchTitle) {
        this.batchTitle = batchTitle;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(String lectureTime) {
        this.lectureTime = lectureTime;
    }

    public String getLectureType() {
        return lectureType;
    }

    public void setLectureType(String lectureType) {
        this.lectureType = lectureType;
    }
}
