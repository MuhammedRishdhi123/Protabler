package com.example.protabler.Model;

import java.io.Serializable;

public class Module {

    private int moduleId;
    private String moduleTitle;

    public Module(int moduleId, String moduleTitle) {
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
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

    @Override
    public String toString() {
        return "Module{" +
                "moduleTitle='" + moduleTitle + '\'' +
                '}';
    }
}
