package com.example.API_Running.dtos;

import com.example.API_Running.models.TrainingPlan;
import com.example.API_Running.models.TrainingWeek;

import java.util.ArrayList;
import java.util.List;

public class TrainingPlanDetailDTO {
    private Long id;
    private String name;
    private String description;
    private Integer numWeeks;
    private String objDistance;
    private String level;
    private PlanCreatorDTO creator;
    private List<TrainingWeekDTO> trainingWeeks;
    private boolean enrolled;
    private List<GroupDTO> groups;

    public TrainingPlanDetailDTO(TrainingPlan trainingPlan, boolean enrolled) {
        this.id = trainingPlan.getId();
        this.name = trainingPlan.getName();
        this.description = trainingPlan.getDescription();
        this.numWeeks = trainingPlan.getWeeks();
        this.objDistance = trainingPlan.getDistanceObjective();
        this.level = trainingPlan.getLevel();
        this.creator = new PlanCreatorDTO(trainingPlan.getCreator());
        this.trainingWeeks = buildTrainingWeek(trainingPlan.getTrainingWeeks());
        this.enrolled = enrolled;
    }

    public List<TrainingWeekDTO> buildTrainingWeek (List<TrainingWeek> trainingWeek) {
        List<TrainingWeekDTO> weeksInfo = new ArrayList<>();
        trainingWeek.stream().forEach(week -> {
            weeksInfo.add(new TrainingWeekDTO(week));
        });
        return weeksInfo;
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

    public PlanCreatorDTO getCreator() {
        return creator;
    }

    public void setCreator(PlanCreatorDTO creator) {
        this.creator = creator;
    }

    public List<TrainingWeekDTO> getTrainingWeeks() {
        return trainingWeeks;
    }

    public void setTrainingWeeks(List<TrainingWeekDTO> trainingWeeks) {
        this.trainingWeeks = trainingWeeks;
    }

    public boolean getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(boolean enrolled) {
        this.enrolled = enrolled;
    }

    public List<GroupDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDTO> groups) {
        this.groups = groups;
    }
}
