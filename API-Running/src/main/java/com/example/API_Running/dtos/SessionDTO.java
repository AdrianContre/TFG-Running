package com.example.API_Running.dtos;

import java.time.LocalTime;

public class SessionDTO {
    private String name;
    private String description;
    private String type;
    private String runningType;
    private Float distance;
    private LocalTime duration;

    public SessionDTO(String name, String description, String type, String runningType, Float distance, LocalTime duration) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.runningType = runningType;
        this.distance = distance;
        this.duration = duration;
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



    @Override
    public String toString() {
        return "SessionDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", runningType='" + runningType + '\'' +
                ", distance=" + distance +
                ", duration=" + duration +
                '}';
    }
}
