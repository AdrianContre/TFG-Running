package com.example.API_Running.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="TrainingGroup")
public class TrainingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "name", nullable = false, unique = true)
    private String name;

    @Column(name= "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;


    @ManyToMany
    @JoinTable(
            name = "group_runner",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "runner_id")
    )
    private Set<Runner> runners;

    @ManyToMany
    @JoinTable(
            name = "group_training_plan",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "training_plan_id")
    )
    private Set<TrainingPlan> trainingPlans;

    public TrainingGroup(){}

    public TrainingGroup(String name, String description,  Trainer trainer, Set<Runner> runners, Set<TrainingPlan> trainingPlans) {
        this.name = name;
        this.description = description;
        this.trainer = trainer;
        this.runners = runners;
        this.trainingPlans = trainingPlans;
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

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Set<Runner> getRunners() {
        return runners;
    }

    public void setRunners(Set<Runner> runners) {
        this.runners = runners;
    }

    public Set<TrainingPlan> getTrainingPlans() {
        return trainingPlans;
    }

    public void setTrainingPlans(Set<TrainingPlan> trainingPlans) {
        this.trainingPlans = trainingPlans;
    }

    public void addTrainingPlan(TrainingPlan tp) {
        this.trainingPlans.add(tp);
    }

    public void removeTrainingPlan(TrainingPlan tp) {
        this.trainingPlans.remove(tp);
    }

    public void removeUser(Runner r) {
        this.runners.remove(r);
    }

    public Boolean belongsUser(Long userId) {
        Boolean belongs = false;
        for (Runner runner : this.runners) {
            if (Objects.equals(runner.getId(), userId)) {
                belongs = true;
            }
        }
        return belongs;
    }
}
