package com.example.protabler.Dto;

import com.example.protabler.Entities.Course;

public class ModuleDTO {
    private int moduleId;
    private String moduleTitle;
    private int moduleCredits;
    private Course course;

    public ModuleDTO(int moduleId,String moduleTitle, int moduleCredits, Course course) {
        this.moduleId=moduleId;
        this.moduleTitle = moduleTitle;
        this.moduleCredits = moduleCredits;
        this.course = course;
    }

    public ModuleDTO() {
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public int getModuleCredits() {
        return moduleCredits;
    }

    public void setModuleCredits(int moduleCredits) {
        this.moduleCredits = moduleCredits;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
