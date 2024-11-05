package com.example.API_Running.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalTime;

@Entity
@DiscriminatorValue("RUNNING")
public class RunningSession extends TrainingSession{

    @Column(name="type", nullable = false)
    private String type;

    @Column(name="distance", nullable = false)
    private Float distance;

    @Column(name="duration", nullable = false)
    private LocalTime duration;

    public RunningSession(String type, Float distance, LocalTime duration) {
        this.type = type;
        this.distance = distance;
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
