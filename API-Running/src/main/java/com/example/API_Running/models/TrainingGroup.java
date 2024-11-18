package com.example.API_Running.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="TrainingGroup")
public class TrainingGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "name", nullable = false)
    private String name;

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

    public TrainingGroup(String name, Trainer trainer, Set<Runner> runners, Set<TrainingPlan> trainingPlans) {
        this.name = name;
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
}
