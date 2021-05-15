package com.example.protabler.Entities;

public class Admin {
    private int adminId;
    private User user;

    public Admin(int adminId, User user) {
        this.adminId = adminId;
        this.user = user;
    }

    public Admin() {
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
