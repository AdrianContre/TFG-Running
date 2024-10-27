package com.example.API_Running.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
}
