package com.example.protabler.Enums;

public enum LectureType {
    LECTURE(1,"Lecture"),
    LABORATORY(2,"Lab");

    private int lectureType;
    private String lectureTypeName;

    LectureType(int lectureType, String lectureTypeName) {
        this.lectureType = lectureType;
        this.lectureTypeName = lectureTypeName;
    }

    public int getLectureType() {
        return lectureType;
    }

    public void setLectureType(int lectureType) {
        this.lectureType = lectureType;
    }

    public String getLectureTypeName() {
        return lectureTypeName;
    }

    public void setLectureTypeName(String lectureTypeName) {
        this.lectureTypeName = lectureTypeName;
    }
}
