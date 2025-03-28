package com.example.API_Running.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


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

    public Integer getNumSessions() {
        Integer counter = 0;
        for (int i = 0; i<7; ++i) {
            if (!(sessions.get(i) instanceof RestSession)) {
                counter += 1;
            }
        }
        return counter;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }

    public void removeSession(TrainingSession session) {
        this.sessions.remove(session);
    }
}
