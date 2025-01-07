package com.example.API_Running.dtos;

import com.example.API_Running.models.Material;

public class MaterialDTO {
    private Long id;
    private String brand;
    private String model;
    private String description;
    private Float wear;
    private byte[] photo;

    public MaterialDTO(Material material) {
        this.id = material.getId();
        this.brand = material.getBrand();
        this.model = material.getModel();
        this.description = material.getDescription();
        this.wear = material.getWear();
        this.photo = material.getPhoto();
    }

    public Long getId(){
        return this.id;
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
        return wear;
    }

    public void setWear(Float wear) {
        this.wear = wear;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
