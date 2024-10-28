package com.example.API_Running.dtos;

import java.time.LocalTime;
import java.util.List;

public class CreateManualActivityDTO {
    private String name;
    private String description;
    private Float distance;
    private LocalTime duration;
    private Float pace;
    private Integer fcAvg;
    private byte[] route;
    private Long runnerId;
    private List<Long> materialsId;

    public CreateManualActivityDTO(String name, String description, Float distance, LocalTime duration, Float pace, Integer fcAvg, byte[] route, Long runnerId, List<Long> materialsId) {
        this.name = name;
        this.description = description;
        this.distance = distance;
        this.duration = duration;
        this.pace = pace;
        this.fcAvg = fcAvg;
        this.route = route;
        this.runnerId = runnerId;
        this.materialsId = materialsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public Float getPace() {
        return pace;
    }

    public void setPace(Float pace) {
        this.pace = pace;
    }

    public Integer getFcAvg() {
        return fcAvg;
    }

    public void setFcAvg(Integer fcAvg) {
        this.fcAvg = fcAvg;
    }

    public byte[] getRoute() {
        return route;
    }

    public void setRoute(byte[] route) {
        this.route = route;
    }

    public Long getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(Long runnerId) {
        this.runnerId = runnerId;
    }

    public List<Long> getMaterialsId() {
        return materialsId;
    }

    public void setMaterialsId(List<Long> materialsId) {
        this.materialsId = materialsId;
    }
}
