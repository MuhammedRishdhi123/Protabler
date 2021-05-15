package com.example.protabler.Entities;

public class Student {
    private int studentId;
    private User user;
    private Batch batch;
    private Course course;

    public Student(int studentId, User user, Batch batch, Course course) {
        this.studentId = studentId;
        this.user = user;
        this.batch = batch;
        this.course = course;
    }

    public Student() {
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
