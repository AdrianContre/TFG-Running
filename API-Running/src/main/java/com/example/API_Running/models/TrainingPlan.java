package com.example.API_Running.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TrainingPlan")
public class TrainingPlan {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="description",nullable = false, length = 1000)
    private String description;

    @Column(name="weeks",nullable = false)
    private Integer weeks;

    @Column(name="distanceObjective",nullable = false)
    private String distanceObjective;

    @Column(name="level",nullable = false)
    private String level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer creator;

    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingWeek> trainingWeeks = new ArrayList<>();

    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingProgress> trainingProgresses;

    @ManyToMany(mappedBy = "trainingPlans")
    private Set<TrainingGroup> groups;

    @Column(name="wearMaterial",nullable = false)
    private String wearMaterial;

    public TrainingPlan() {}

    public TrainingPlan(String name, String description, Integer weeks, String distanceObjective, String level, Trainer creator, List<TrainingWeek> trainingWeeks, List<TrainingProgress> trainingProgresses) {
        this.name = name;
        this.description = description;
        this.weeks = weeks;
        this.distanceObjective = distanceObjective;
        this.level = level;
        this.creator = creator;
        this.trainingWeeks = trainingWeeks;
        this.trainingProgresses = trainingProgresses;
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

    public Integer getWeeks() {
        return weeks;
    }

    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }

    public String getDistanceObjective() {
        return distanceObjective;
    }

    public void setDistanceObjective(String distanceObjective) {
        this.distanceObjective = distanceObjective;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Trainer getCreator() {
        return creator;
    }

    public void setCreator(Trainer creator) {
        this.creator = creator;
    }

    public List<TrainingWeek> getTrainingWeeks() {
        return trainingWeeks;
    }

    public void setTrainingWeeks(List<TrainingWeek> trainingWeeks) {
        this.trainingWeeks = trainingWeeks;
    }

    public List<TrainingProgress> getTrainingProgresses() {
        return trainingProgresses;
    }

    public void setTrainingProgresses(List<TrainingProgress> trainingProgresses) {
        this.trainingProgresses = trainingProgresses;
    }

    public Set<TrainingGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<TrainingGroup> groups) {
        this.groups = groups;
    }

    public void addTrainningProgress (TrainingProgress trainingProgress) {
        this.trainingProgresses.add(trainingProgress);
    }

    public Integer totalSessions () {
        Integer counter = 0;
        for (int i = 0; i < this.trainingWeeks.size(); ++i) {
            counter = counter + this.trainingWeeks.get(i).getNumSessions();
        }
        return counter;
    }

    public void removeGroup(TrainingGroup group) {
        this.groups.remove(group);
    }

    public void removeProgress(TrainingProgress tp) {
        this.trainingProgresses.remove(tp);
    }

    public void removeWeek(TrainingWeek week) {
        this.trainingWeeks.remove(week);
    }

    public String getWearMaterial() {
        return wearMaterial;
    }

    public void setWearMaterial(String wearMaterial) {
        this.wearMaterial = wearMaterial;
    }
}
