package com.example.protabler.Enums;

public enum LectureTime {
    FIRST(1,"1 (8:30 - 10:00)"),
    SECOND(2, "2 (10:15 - 12:30)"),
    THIRD(3, "3 (13:30 - 15:00)"),
    FOURTH(4, "4 (15:30 - 16:00)"),
    FIFTH(5, "5 (16:00 - 17:30)");

    private int lectureTime;
    private String lectureTimeName;

    LectureTime(int lectureTime, String lectureTimeName) {
        this.lectureTime = lectureTime;
        this.lectureTimeName = lectureTimeName;
    }

    public int getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(int lectureTime) {
        this.lectureTime = lectureTime;
    }

    public String getLectureTimeName() {
        return lectureTimeName;
    }

    public void setLectureTimeName(String lectureTimeName) {
        this.lectureTimeName = lectureTimeName;
    }
}
