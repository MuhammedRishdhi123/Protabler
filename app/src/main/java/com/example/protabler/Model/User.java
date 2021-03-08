package com.example.protabler.Model;

public class User {

    private int userId;
    private String name,email,curriculum,password,phone,batchCode;

    public User(int userId, String name, String email, String curriculum, String password, String phone, String batchCode) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.curriculum = curriculum;
        this.password = password;
        this.phone = phone;
        this.batchCode = batchCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }
}
