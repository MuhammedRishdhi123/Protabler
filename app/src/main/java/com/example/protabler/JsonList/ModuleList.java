package com.example.protabler.JsonList;

import com.example.protabler.Dto.ModuleDTO;
import com.example.protabler.Dto.SessionDTO;
import com.example.protabler.Entities.Module;

public class ModuleList {
    private java.util.List<ModuleDTO> List;

    public java.util.List<ModuleDTO> getModuleList() {
        return List;
    }

    public void setModuleList(java.util.List<ModuleDTO> list) {
        this.List = list;
    }
}
