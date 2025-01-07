package com.example.API_Running.dtos;

import java.util.List;

public class CreateTrainingGroupDTO {
    private String name;
    private String description;
    private Long trainerId;
    private List<Long> runnersId;

    public CreateTrainingGroupDTO(String name, String description,  Long trainerId, List<Long> runnersId) {
        this.name = name;
        this.description = description;
        this.trainerId = trainerId;
        this.runnersId = runnersId;
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

    public List<Long> getRunnersId() {
        return runnersId;
    }

    public void setRunnersId(List<Long> runnersId) {
        this.runnersId = runnersId;
    }
}
