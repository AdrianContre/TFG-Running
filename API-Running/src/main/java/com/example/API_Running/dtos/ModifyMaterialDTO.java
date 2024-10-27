package com.example.API_Running.dtos;

public class ModifyMaterialDTO {
    private String brand;
    private String model;
    private String description;
    private Integer wear;

    public ModifyMaterialDTO(String brand, String model, String description, Integer wear) {
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.wear = wear;
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
