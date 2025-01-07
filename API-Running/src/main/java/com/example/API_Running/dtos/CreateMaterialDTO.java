package com.example.API_Running.dtos;

public class CreateMaterialDTO {

    private String brand;
    private String model;
    private String description;
    private Float wear;
    private Long runnerId;

    public CreateMaterialDTO(String brand, String model, String description, Float wear, Long runnerId) {
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.wear = wear;
        this.runnerId = runnerId;
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

    public void setWear (Float wear) {
        this.wear = wear;
    }

    public Long getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(Long runnerId) {
        this.runnerId = runnerId;
    }
}
