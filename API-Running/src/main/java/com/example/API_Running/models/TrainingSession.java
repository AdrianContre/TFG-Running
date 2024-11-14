package com.example.API_Running.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "session_type")
@Entity
@Table(name="TrainingSession")
public abstract class TrainingSession {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description", nullable = false,length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_week_id")
    private TrainingWeek trainingWeek;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<TrainingSessionResult> results = new ArrayList<>();

    @Column(name = "day", nullable = false)
    private Integer day;

    public TrainingSession() {}

    public TrainingSession(Long id, String name, String description, TrainingWeek trainingWeek, List<TrainingSessionResult> results, Integer day) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.trainingWeek = trainingWeek;
        this.results = results;
        this.day = day;
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

    public TrainingWeek getTrainingWeek() {
        return trainingWeek;
    }

    public void setTrainingWeek(TrainingWeek trainingWeek) {
        this.trainingWeek = trainingWeek;
    }

    public List<TrainingSessionResult> getResults() {
        return results;
    }

    public void setResults(List<TrainingSessionResult> results) {
        this.results = results;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public void addResult(TrainingSessionResult result) {
        this.results.add(result);
    }
}
