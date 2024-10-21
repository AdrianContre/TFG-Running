package com.example.API_Running.models;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("RUNNER")
public class Runner extends User {

    @Column(name="weight",nullable = false)
    private Integer weight;

    @Column(name="height",nullable = false)
    private Integer height;

    @Column(name="fcMax",nullable = false)
    private Integer fcMax;

    @Column(name="isTrainer", nullable = false)
    private boolean isTrainer;

    @OneToOne(mappedBy = "runner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Trainer trainerProfile;

    public Runner() {}

    public Runner(Integer weight, Integer height, Integer fcMax,boolean isTrainer) {
        this.weight = weight;
        this.height = height;
        this.fcMax = fcMax;
        this.isTrainer = isTrainer;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getFcMax() {
        return fcMax;
    }

    public void setFcMax(Integer fcMax) {
        this.fcMax = fcMax;
    }

    public boolean getIsTrainer() {
        return this.isTrainer;
    }

    public void setIsTrainer (boolean isTrainer) {
        this.isTrainer = isTrainer;
    }

    public Trainer getTrainerProfile() {
        return trainerProfile;
    }

    public void setTrainerProfile(Trainer trainerProfile) {
        this.trainerProfile = trainerProfile;
    }
}
