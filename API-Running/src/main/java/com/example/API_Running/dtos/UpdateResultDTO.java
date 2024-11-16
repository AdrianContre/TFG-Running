package com.example.API_Running.dtos;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class UpdateResultDTO {
    private String type;
    private String description;
    private Integer effort;
    private LocalDateTime date;
    private List<Long> materialsId;
    private Float distance;
    private LocalTime duration;
    private Float pace;
    private Integer fcAvg;

    public UpdateResultDTO(String type, String description, Integer effort, LocalDateTime date, List<Long> materialsId, Float distance, LocalTime duration, Float pace, Integer fcAvg) {
        this.type = type;
        this.description = description;
        this.effort = effort;
        this.date = date;
        this.materialsId = materialsId;
        this.distance = distance;
        this.duration = duration;
        this.pace = pace;
        this.fcAvg = fcAvg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEffort() {
        return effort;
    }

    public void setEffort(Integer effort) {
        this.effort = effort;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Long> getMaterialsId() {
        return materialsId;
    }

    public void setMaterialsId(List<Long> materialsId) {
        this.materialsId = materialsId;
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

    @Override
    public String toString() {
        return "UpdateResultDTO{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", effort=" + effort +
                ", date=" + date +
                ", materialsId=" + materialsId +
                ", distance=" + distance +
                ", duration=" + duration +
                ", pace=" + pace +
                ", fcAvg=" + fcAvg +
                '}';
    }
}
