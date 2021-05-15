package com.example.protabler.Entities;

import com.example.protabler.Enums.Day;
import com.example.protabler.Enums.LectureTime;
import com.example.protabler.Enums.LectureType;

public class Session {
    private int sessionId;
    private Batch batch;
    private Room room;
    private Day day;
    private LectureType lectureType;
    private LectureTime lectureTime;
    private Lecturer lecturer;
    private Module module;


    public Session(int sessionId, Batch batch, Room room, Day day, LectureType lectureType, LectureTime lectureTime, Lecturer lecturer, Module module) {
        this.sessionId = sessionId;
        this.batch = batch;
        this.room = room;
        this.day = day;
        this.lectureType = lectureType;
        this.lectureTime = lectureTime;
        this.lecturer = lecturer;
        this.module = module;
    }

    public Session() {
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public LectureType getLectureType() {
        return lectureType;
    }

    public void setLectureType(LectureType lectureType) {
        this.lectureType = lectureType;
    }

    public LectureTime getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(LectureTime lectureTime) {
        this.lectureTime = lectureTime;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
