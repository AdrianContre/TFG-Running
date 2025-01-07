package com.example.API_Running.dtos;

import com.example.API_Running.models.Activity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ActivityDTO {
    private Long id;
    private String name;
    private Float distance;
    private LocalTime duration;
    private LocalDateTime date;
    private String type;

    public ActivityDTO(Long id, String name, Float distance, LocalTime duration,LocalDateTime date, String type) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.duration = duration;
        this.date = date;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId (Long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public String getType() {
        return type;
    }

    public void setActivityType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
