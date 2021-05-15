package com.example.protabler.Entities;

import java.util.List;

public class Batch {
    private int batchId;
    private String batchTitle;
    private List<Student> students;
    private List<Batch> batches;

    public Batch(int batchId, String batchTitle, List<Student> students, List<Batch> batches) {
        this.batchId = batchId;
        this.batchTitle = batchTitle;
        this.students = students;
        this.batches = batches;
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

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }
}
