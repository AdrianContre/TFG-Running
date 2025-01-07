package com.example.API_Running.dtos;

import com.example.API_Running.models.TrainingPlan;

public class ListPlansDTO {
    private Long id;
    private String name;
    private String objDistance;
    private String level;
    private String trainerName;
    private String trainerSurname;
    private String wearMaterial;

    public ListPlansDTO(TrainingPlan trainingPlan) {
        this.id = trainingPlan.getId();
        this.name = trainingPlan.getName();
        this.objDistance = trainingPlan.getDistanceObjective();
        this.level = trainingPlan.getLevel();
        this.trainerName = trainingPlan.getCreator().getName();
        this.trainerSurname = trainingPlan.getCreator().getSurname();
        this.wearMaterial = trainingPlan.getWearMaterial();
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

    public String getWearMaterial() {
        return wearMaterial;
    }

    public void setWearMaterial(String wearMaterial) {
        this.wearMaterial = wearMaterial;
    }
}


