package com.example.protabler.Entities;

import java.util.List;

public class Lecturer {
 private int lecturerId;
 private User user;
 private List<Module> modules;
 private List<Session> sessions;

    public Lecturer(int lecturerId, User user, List<Module> modules, List<Session> sessions) {
        this.lecturerId = lecturerId;
        this.user = user;
        this.modules = modules;
        this.sessions = sessions;
    }

    public Lecturer() {
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }
}
