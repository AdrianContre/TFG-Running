package com.example.API_Running.dtos;

import java.util.List;

public class CreateTrainingGroupDTO {
    private String name;
    private Long trainerId;
    private List<Long> runnersId;

    public CreateTrainingGroupDTO(String name, Long trainerId, List<Long> runnersId) {
        this.name = name;
        this.trainerId = trainerId;
        this.runnersId = runnersId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
