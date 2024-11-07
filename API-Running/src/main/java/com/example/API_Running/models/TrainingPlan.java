package com.example.API_Running.models;

import com.example.API_Running.enums.DistanceObjective;
import com.example.API_Running.enums.RunnerLevel;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
}
