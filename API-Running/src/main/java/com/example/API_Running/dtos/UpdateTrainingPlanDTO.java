package com.example.API_Running.dtos;

import java.util.List;
import java.util.Set;

public class UpdateTrainingPlanDTO {
    private String name;
    private String description;
    private Integer numWeeks;
    private String objDistance;
    private String level;
    private List<List<SessionDTO>> sessions;
    private List<Long> groupsId;
    private String wearMaterial;

    public UpdateTrainingPlanDTO(String name, String description, Integer numWeeks, String objDistance, String level, List<List<SessionDTO>> sessions, List<Long> groupsId, String wearMaterial) {
        this.name = name;
        this.description = description;
        this.numWeeks = numWeeks;
        this.objDistance = objDistance;
        this.level = level;
        this.sessions = sessions;
        this.groupsId = groupsId;
        this.wearMaterial = wearMaterial;
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

    public Integer getNumWeeks() {
        return numWeeks;
    }

    public void setNumWeeks(Integer numWeeks) {
        this.numWeeks = numWeeks;
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

    public List<List<SessionDTO>> getSessions() {
        return sessions;
    }

    public void setSessions(List<List<SessionDTO>> sessions) {
        this.sessions = sessions;
    }

    public List<Long> getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(List<Long> groupsId) {
        this.groupsId = groupsId;
    }

    public String getWearMaterial() {
        return wearMaterial;
    }

    public void setWearMaterial(String wearMaterial) {
        this.wearMaterial = wearMaterial;
    }

    @Override
    public String toString() {
        return "UpdateTrainingPlanDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", numWeeks=" + numWeeks +
                ", objDistance='" + objDistance + '\'' +
                ", level='" + level + '\'' +
                ", sessions=" + sessions +
                '}';
    }
}
