package com.example.API_Running.dtos;

import com.example.API_Running.models.TrainingGroup;

import java.util.List;

public class GroupDetailsDTO {
    private Long id;
    private String name;
    private String description;
    private String trainerFullInfo;
    private List<String> members;

    public GroupDetailsDTO(Long id, String name, String description, String trainerFullInfo, List<String> members) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.trainerFullInfo = trainerFullInfo;
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTrainerFullInfo() {
        return trainerFullInfo;
    }

    public void setTrainerFullInfo(String trainerFullInfo) {
        this.trainerFullInfo = trainerFullInfo;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
