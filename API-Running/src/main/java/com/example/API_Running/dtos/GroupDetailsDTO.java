package com.example.API_Running.dtos;

import com.example.API_Running.models.TrainingGroup;

import java.util.List;

public class GroupDetailsDTO {
    private Long id;
    private String name;
    private String description;
    private Long trainerId;
    private String trainerFullInfo;
    private List<UserDTO> members;

    public GroupDetailsDTO(Long id, String name, String description, Long trainerId, String trainerFullInfo, List<UserDTO> members) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.trainerId = trainerId;
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

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerFullInfo() {
        return trainerFullInfo;
    }

    public void setTrainerFullInfo(String trainerFullInfo) {
        this.trainerFullInfo = trainerFullInfo;
    }

    public List<UserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserDTO> members) {
        this.members = members;
    }
}
