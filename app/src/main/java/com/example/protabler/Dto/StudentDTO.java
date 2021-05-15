package com.example.protabler.Dto;

import com.google.gson.annotations.SerializedName;

public class StudentDTO {

    private int studentId;

    private String studentName;

    private String studentEmail;

    private String courseName;
    private String batchTitle;
    private String studentPhone;
    private String password;


    public StudentDTO(int studentId, String studentName, String studentEmail, String courseName, String batchTitle,String studentPhone) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.courseName = courseName;
        this.batchTitle = batchTitle;
        this.studentPhone=studentPhone;

    }

    public StudentDTO() {
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getBatchTitle() {
        return batchTitle;
    }

    public void setBatchTitle(String batchTitle) {
        this.batchTitle = batchTitle;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
