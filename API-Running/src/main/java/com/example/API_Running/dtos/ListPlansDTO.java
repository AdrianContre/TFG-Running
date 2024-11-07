package com.example.API_Running.dtos;

import com.example.API_Running.models.TrainingPlan;

public class ListPlansDTO {
    private String name;
    private String objDistance;
    private String level;
    private String trainerName;
    private String trainerSurname;

    public ListPlansDTO(TrainingPlan trainingPlan) {
        this.name = trainingPlan.getName();
        this.objDistance = trainingPlan.getDistanceObjective();
        this.level = trainingPlan.getLevel();
        this.trainerName = trainingPlan.getCreator().getName();
        this.trainerSurname = trainingPlan.getCreator().getSurname();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjDistance() {
        return objDistance;
    }

    public void setObjDistance(String objDistance) {
        this.objDistance = objDistance;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTrainerSurname() {
        return trainerSurname;
    }

    public void setTrainerSurname(String trainerSurname) {
        this.trainerSurname = trainerSurname;
    }
}


