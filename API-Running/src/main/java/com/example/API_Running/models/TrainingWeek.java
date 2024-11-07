package com.example.API_Running.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="TrainingWeek")
public class TrainingWeek {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_plan_id")
    private TrainingPlan trainingPlan;

    @OneToMany(mappedBy = "trainingWeek", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingSession> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "trainingWeek", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public TrainingWeek() {}

    public TrainingWeek(TrainingPlan trainingPlan, List<TrainingSession> sessions, List<Comment> comments) {
        this.trainingPlan = trainingPlan;
        this.sessions = sessions;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public List<TrainingSession> getSessions() {
        return sessions;
    }

    public void setSessions(List<TrainingSession> sessions) {
        this.sessions = sessions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
