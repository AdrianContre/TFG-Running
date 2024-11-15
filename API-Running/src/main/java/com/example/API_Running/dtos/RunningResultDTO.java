package com.example.API_Running.dtos;

import com.example.API_Running.models.Material;
import com.example.API_Running.models.RunningSessionResult;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RunningResultDTO {
    private Long id;
    private String planName;
    private String name;
    private String description;
    private LocalDateTime date;
    private Float distance;
    private Float pace;
    private LocalTime duration;
    private Integer fcAvg;
    private Integer effort;
    private List<String> materials;
    private byte[] route;

    public RunningResultDTO(RunningSessionResult runningSessionResult, String planName) {
        this.id = runningSessionResult.getId();
        this.planName = planName;
        this.name = runningSessionResult.getName();
        this.description = runningSessionResult.getDescription();
        this.date = runningSessionResult.getDate();
        this.distance = runningSessionResult.getDistance();
        this.pace = runningSessionResult.getPace();
        this.duration = runningSessionResult.getDuration();
        this.fcAvg = runningSessionResult.getFcAvg();
        this.effort = runningSessionResult.getEffort();
        this.materials = new ArrayList<>();

        if (runningSessionResult.getRoute() != null) {
            this.route = runningSessionResult.getRoute();
        }
        else {
            this.route = null;
        }
        Set<Material> materials1 = runningSessionResult.getMaterials();
        materials1.forEach(mat -> {
            String nameMat = mat.getBrand() + " " + mat.getModel();
            this.materials.add(nameMat);
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getPace() {
        return pace;
    }

    public void setPace(Float pace) {
        this.pace = pace;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public Integer getFcAvg() {
        return fcAvg;
    }

    public void setFcAvg(Integer fcAvg) {
        this.fcAvg = fcAvg;
    }

    public Integer getEffort() {
        return effort;
    }

    public void setEffort(Integer effort) {
        this.effort = effort;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public byte[] getRoute() {
        return route;
    }

    public void setRoute(byte[] route) {
        this.route = route;
    }
}
