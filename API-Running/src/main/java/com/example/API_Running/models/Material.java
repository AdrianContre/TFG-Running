package com.example.API_Running.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Material")
public class Material {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="brand",nullable = false)
    private String brand;

    @Column(name="model",nullable = false)
    private String model;

    @Column(name="description",nullable = false)
    private String description;

    @Column(name="wear",nullable = false)
    private Float wear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "runner_id")
    private Runner runner;

    @Lob
    @Column(name="photo",nullable = true, columnDefinition = "MEDIUMBLOB")
    private byte[] photo;

    public Material(){}

    public Material(String brand, String model, String description, /*Integer wear*/Float wear, Runner runner) {
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.wear = wear;
        this.runner = runner;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getWear() {
        return this.wear;
    }

    public void setWear(Float wear) {
        this.wear = wear;
    }

    public Runner getRunner() {
        return runner;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public void addMileage (Float mileage) {
        this.wear += mileage;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
