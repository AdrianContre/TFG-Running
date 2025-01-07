package com.example.API_Running.dtos;

import com.example.API_Running.models.TrainingPlan;

public class EnrolledPlanDTO {
    private Long id;
    private String name;
    private String objDistance;
    private String level;
    private String trainerName;
    private String trainerSurname;
    private Float percentage;
    private Integer numSessions;
    private Integer sessionsCompleted;
    private String wearMaterial;

    public EnrolledPlanDTO(TrainingPlan trainingPlan, Float percentage) {
        this.id = trainingPlan.getId();
        this.name = trainingPlan.getName();
        this.objDistance = trainingPlan.getDistanceObjective();
        this.level = trainingPlan.getLevel();
        this.trainerName = trainingPlan.getCreator().getName();
        this.trainerSurname = trainingPlan.getCreator().getSurname();
        this.percentage = percentage;
        this.numSessions = trainingPlan.totalSessions();
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

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Integer getNumSessions() {
        return numSessions;
    }

    public void setNumSessions(Integer numSessions) {
        this.numSessions = numSessions;
    }

    public Integer getSessionsCompleted() {
        return sessionsCompleted;
    }

    public void setSessionsCompleted(Integer sessionsCompleted) {
        this.sessionsCompleted = sessionsCompleted;
    }

    public String getWearMaterial() {
        return wearMaterial;
    }

    public void setWearMaterial(String wearMaterial) {
        this.wearMaterial = wearMaterial;
    }
}
