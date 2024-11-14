package com.example.API_Running.dtos;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CreateRunningSessionResultDTO {
    private Long planId;
    private Long userId;
    private Long sessionId;
    private String description;
    private Integer effort;
    private Float distance;
    private LocalTime duration;
    private Float pace;
    private Integer fcAvg;
    private List<Long> materialsId;
    private LocalDateTime date;

    public CreateRunningSessionResultDTO(Long planId, Long userId, Long sessionId, String description, Integer effort, Float distance, LocalTime duration, Float pace, Integer fcAvg, List<Long> materialsId, LocalDateTime date) {
        this.planId = planId;
        this.userId = userId;
        this.sessionId = sessionId;
        this.description = description;
        this.effort = effort;
        this.distance = distance;
        this.duration = duration;
        this.pace = pace;
        this.fcAvg = fcAvg;
        this.materialsId = materialsId;
        this.date = date;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
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

    public List<Long> getMaterialsId() {
        return materialsId;
    }

    public void setMaterialsId(List<Long> materialsId) {
        this.materialsId = materialsId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
