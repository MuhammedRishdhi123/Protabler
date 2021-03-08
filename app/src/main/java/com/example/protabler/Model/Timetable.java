package com.example.protabler.Model;

public class Timetable {
    private int timetableId;
    private String batchCode;
    private String CourseTitle;

    public Timetable(int timetableId, String batchCode, String courseTitle) {
        this.timetableId = timetableId;
        this.batchCode = batchCode;
        CourseTitle = courseTitle;
    }

    public Timetable(String batchCode, String courseTitle) {
        this.batchCode = batchCode;
        CourseTitle = courseTitle;
    }

    public int getTimetableId() {
        return timetableId;
    }

    public void setTimetableId(int timetableId) {
        this.timetableId = timetableId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getCourseTitle() {
        return CourseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        CourseTitle = courseTitle;
    }
}
