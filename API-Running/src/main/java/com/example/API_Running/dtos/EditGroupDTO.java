package com.example.API_Running.dtos;

import java.util.List;

public class EditGroupDTO {
    private String name;
    private String description;
    private List<Long> membersId;

    public EditGroupDTO(String name, String description, List<Long> membersId) {
        this.name = name;
        this.description = description;
        this.membersId = membersId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getMembersId() {
        return membersId;
    }

    public void setMembersId(List<Long> membersId) {
        this.membersId = membersId;
    }
}
