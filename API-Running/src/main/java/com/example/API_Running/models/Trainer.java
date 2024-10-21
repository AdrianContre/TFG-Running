package com.example.API_Running.models;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("TRAINER")
public class Trainer extends Runner {

    @Column(name="experience", nullable = false)
    private Integer experience;

    @OneToOne
    @JoinColumn(name = "runner_id")
    private Runner runner;

    public Trainer() {}

    public Trainer(Integer weight, Integer height, Integer fcMax, Integer experience,boolean isTrainer, Runner runner) {
        super(weight, height, fcMax,isTrainer);
        this.experience = experience;
        this.runner = runner;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }
}
