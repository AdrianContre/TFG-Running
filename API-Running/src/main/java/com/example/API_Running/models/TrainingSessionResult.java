package com.example.API_Running.models;

import jakarta.persistence.*;


@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "session_result_type")
@Entity
@Table(name="TrainingSessionResult")
public abstract class TrainingSessionResult {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="effort", nullable = false)
    private Integer effort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_session_id")
    private TrainingSession session;

    public TrainingSessionResult() {
    }

    public TrainingSessionResult(Long id, Integer effort, TrainingSession session) {
        this.id = id;
        this.effort = effort;
        this.session = session;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEffort() {
        return effort;
    }

    public void setEffort(Integer effort) {
        this.effort = effort;
    }

    public TrainingSession getSession() {
        return session;
    }

    public void setSession(TrainingSession session) {
        this.session = session;
    }
}
