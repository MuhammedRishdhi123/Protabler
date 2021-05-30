package com.example.protabler.Entities;

import java.util.List;

public class Batch {
    private int batchId;
    private String batchTitle;
    private List<Student> students;
    private List<Session> sessions;

    public Batch(int batchId, String batchTitle, List<Student> students, List<Session> sessions) {
        this.batchId = batchId;
        this.batchTitle = batchTitle;
        this.students = students;
        this.sessions = sessions;
    }

    public Batch() {
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getBatchTitle() {
        return batchTitle;
    }

    public void setBatchTitle(String batchTitle) {
        this.batchTitle = batchTitle;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
