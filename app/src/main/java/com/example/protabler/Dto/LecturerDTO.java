package com.example.protabler.Dto;

public class LecturerDTO {
    private int lecturerId;
    private String lecturerName;
    private String lecturerEmail;
    private String lecturerPassword;
    private String lecturerPhone;

    public LecturerDTO(int lecturerId, String lecturerName, String lecturerEmail, String lecturerPassword, String lecturerPhone) {
        this.lecturerId = lecturerId;
        this.lecturerName = lecturerName;
        this.lecturerEmail = lecturerEmail;
        this.lecturerPassword = lecturerPassword;
        this.lecturerPhone = lecturerPhone;
    }

    public LecturerDTO() {
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getLecturerEmail() {
        return lecturerEmail;
    }

    public void setLecturerEmail(String lecturerEmail) {
        this.lecturerEmail = lecturerEmail;
    }

    public String getLecturerPassword() {
        return lecturerPassword;
    }

    public void setLecturerPassword(String lecturerPassword) {
        this.lecturerPassword = lecturerPassword;
    }

    public String getLecturerPhone() {
        return lecturerPhone;
    }

    public void setLecturerPhone(String lecturerPhone) {
        this.lecturerPhone = lecturerPhone;
    }
}
