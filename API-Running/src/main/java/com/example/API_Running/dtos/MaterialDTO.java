package com.example.API_Running.dtos;

import com.example.API_Running.models.Material;

public class MaterialDTO {
    private Long id;
    private String brand;
    private String model;
    private String description;
    private Integer wear;

    public MaterialDTO(Material material) {
        this.id = material.getId();
        this.brand = material.getBrand();
        this.model = material.getModel();
        this.description = material.getDescription();
        this.wear = material.getWear();
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

    public Integer getWear() {
        return wear;
    }

    public void setWear(Integer wear) {
        this.wear = wear;
    }
}
