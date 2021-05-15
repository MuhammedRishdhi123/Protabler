package com.example.protabler.JsonList;

import com.example.protabler.Dto.SessionDTO;
import com.example.protabler.Entities.Session;

import java.util.List;


public class SessionList {
    private List<SessionDTO> List;

    public List<SessionDTO> getSessionList() {
        return List;
    }

    public void setSessionList(List<SessionDTO> list) {
        this.List = list;
    }
}
