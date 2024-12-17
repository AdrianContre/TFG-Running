package com.example.API_Running.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@DiscriminatorValue("TRAINER")
public class Trainer extends Runner {

    @Column(name="experience", nullable = false)
    private Integer experience;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingPlan> plans = new ArrayList<>();

    @OneToMany(mappedBy = "trainer")
    private Set<TrainingGroup> managedGroups;

    public Trainer() {}

    public Trainer(Integer weight, Integer height, Integer fcMax, Integer experience) {
        super(weight, height, fcMax);
        this.experience = experience;
        this.managedGroups = new HashSet<>();
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public List<TrainingPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<TrainingPlan> plans) {
        this.plans = plans;
    }

    public Set<TrainingGroup> getManagedGroups() {
        return managedGroups;
    }

    public void setManagedGroups(Set<TrainingGroup> managedGroups) {
        this.managedGroups = managedGroups;
    }

    public void addManagedGroups(TrainingGroup group) {
        this.managedGroups.add(group);
    }

    public void removeManagedGroups(TrainingGroup group) {
        this.managedGroups.remove(group);
    }

    public void removeTrainingPlan(TrainingPlan plan) {
        plans.remove(plan);
    }
}
