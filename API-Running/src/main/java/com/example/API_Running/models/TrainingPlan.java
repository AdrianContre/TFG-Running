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

    @Column(name="description",nullable = false)
    private String description;

    @Column(name="weeks",nullable = false)
    private Integer weeks;

    @Column(name="distanceObjective",nullable = false)
    private DistanceObjective distanceObjective;

    @Column(name="level",nullable = false)
    private RunnerLevel level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer creator;

//    @ManyToMany
//    @JoinTable(
//            name = "clients",
//            joinColumns = @JoinColumn(name = "training_plan_id"),
//            inverseJoinColumns = @JoinColumn(name = "runner_id")
//    )
//    private List<Runner> clients = new ArrayList<>();

    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingWeek> trainingWeeks = new ArrayList<>();

    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingProgress> trainingProgresses;

    public TrainingPlan() {}

    public TrainingPlan(Long id, String name, String description, Integer weeks, DistanceObjective distanceObjective, RunnerLevel level, Trainer creator/*, List<Runner> clients*/, List<TrainingWeek> trainingWeeks, List<TrainingProgress> trainingProgresses) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weeks = weeks;
        this.distanceObjective = distanceObjective;
        this.level = level;
        this.creator = creator;
        //this.clients = clients;
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

    public DistanceObjective getDistanceObjective() {
        return distanceObjective;
    }

    public void setDistanceObjective(DistanceObjective distanceObjective) {
        this.distanceObjective = distanceObjective;
    }

    public RunnerLevel getLevel() {
        return level;
    }

    public void setLevel(RunnerLevel level) {
        this.level = level;
    }

    public Trainer getCreator() {
        return creator;
    }

    public void setCreator(Trainer creator) {
        this.creator = creator;
    }

//    public List<Runner> getClients() {
//        return clients;
//    }
//
//    public void setClients(List<Runner> clients) {
//        this.clients = clients;
//    }

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
