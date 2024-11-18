package com.example.API_Running.dtos;

import java.time.LocalTime;

public class SessionDetailsDTO {
    private Long id;
    private String name;
    private String description;
    private String type;
    private String runningType;
    private Float distance;
    private LocalTime duration;

    public SessionDetailsDTO(Long id, String name, String description, String type, String runningType, Float distance, LocalTime duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.runningType = runningType;
        this.distance = distance;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRunningType() {
        return runningType;
    }

    public void setRunningType(String runningType) {
        this.runningType = runningType;
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
}
