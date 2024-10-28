package com.example.API_Running.models;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalTime;

@Entity
@DiscriminatorValue("MANUAL_ACTIVTY")
public class ManualActivity extends Activity {

    @Column(name="name",nullable = false)
    private Float distance;

    @Column(name="duration",nullable = false)
    private LocalTime duration;

    @Column(name="pace",nullable = false)
    private Float pace;

    @Column(name="fcAvg",nullable = false)
    private Integer fcAvg;

    @Column(name="route",nullable = true)
    private byte[] route;

    public ManualActivity(){}

    public ManualActivity(Float distance, LocalTime duration, Float pace, Integer fcAvg) {
        this.distance = distance;
        this.duration = duration;
        this.pace = pace;
        this.fcAvg = fcAvg;
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
}
