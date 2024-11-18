package com.example.API_Running.dtos;


import com.example.API_Running.models.TrainingSessionResult;

import java.time.LocalDateTime;

public class TrainingProgressDTO {
    private Long resultId;
    private String userFullName;
    private String username;
    private String sessionName;
    private LocalDateTime date;
    private String type;

    public TrainingProgressDTO(TrainingSessionResult result) {
        this.resultId = result.getId();
        this.userFullName = result.getRunner().getName() + " " + result.getRunner().getSurname();
        this.username = result.getRunner().getUsername();
        this.sessionName = result.getSession().getName();
        this.date = result.getDate();
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
