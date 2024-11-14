package com.example.API_Running.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class CreateMobilitySessionResultDTO {
    private Long planId;
    private Long userId;
    private Long sessionId;
    private String description;
    private Integer effort;
    private List<Long> materialsId;
    private LocalDateTime date;

    public CreateMobilitySessionResultDTO(Long planId, Long userId, Long sessionId, String description, Integer effort, List<Long> materialsId, LocalDateTime date) {
        this.planId = planId;
        this.userId = userId;
        this.sessionId = sessionId;
        this.description = description;
        this.effort = effort;
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
