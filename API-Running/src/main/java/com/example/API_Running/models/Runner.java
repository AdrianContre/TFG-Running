package com.example.API_Running.models;

import jakarta.persistence.*;

import java.util.*;

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

    @OneToMany(mappedBy = "runner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Material> materials = new ArrayList<>();

    @ManyToMany(mappedBy = "runners")
    private Set<TrainingGroup> groups;

    @OneToMany(mappedBy = "runner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingProgress> plansProgress = new ArrayList<>();

    public Runner() {}

    public Runner(Integer weight, Integer height, Integer fcMax,boolean isTrainer) {
        this.weight = weight;
        this.height = height;
        this.fcMax = fcMax;
        this.isTrainer = isTrainer;
        this.groups = new HashSet<>();
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

    public ArrayList<Integer> getZones() {
        ArrayList<Integer> zones = new ArrayList<>();
        Integer maxZ1 = (int) (this.fcMax * 0.70) - 1;
        Integer maxZ2 = (int) (this.fcMax * 0.80) - 1;
        Integer maxZ3 = (int) (this.fcMax * 0.88) - 1;
        Integer maxZ4 = (int) (this.fcMax * 0.92) - 1;
        Integer maxZ5 = this.fcMax;
        zones.add(maxZ1);
        zones.add(maxZ2);
        zones.add(maxZ3);
        zones.add(maxZ4);
        zones.add(maxZ5);
        return zones;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public void addMaterial (Material material) {
        this.materials.add(material);
    }

    public boolean isTrainer() {
        return isTrainer;
    }

    public void setTrainer(boolean trainer) {
        isTrainer = trainer;
    }


    public List<TrainingProgress> getPlansProgress() {
        return plansProgress;
    }

    public void setPlansProgress(List<TrainingProgress> plansProgress) {
        this.plansProgress = plansProgress;
    }

    public Set<TrainingGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<TrainingGroup> groups) {
        this.groups = groups;
    }

    public void addGroup(TrainingGroup group) {
        this.groups.add(group);
    }

    public void removeGroup(TrainingGroup g) {
        this.groups.remove(g);
    }

    public void removeTrainingProgress(TrainingProgress tp) {
        this.plansProgress.remove(tp);
    }

    public HashMap<String, Float> getMostUsedMaterial() {
        HashMap<String, Float> material = new HashMap<>();
        Float max = (float) 0;
        Material m = null;
        for (Material mat : this.materials) {
            if (mat.getWear() > max) {
                max = mat.getWear();
                m = mat;
            }
        }
        if (m == null) {
            return null;
        }
        String materialName = m.getBrand() + " " + m.getModel();
        material.put(materialName, max);
        return material;
    }
}
