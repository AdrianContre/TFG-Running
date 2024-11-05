package com.example.API_Running.models;

import jakarta.persistence.*;


@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "session_result_type")
@Entity
@Table(name="TrainingSessionResult")
public abstract class TrainingSessionResult extends Activity{


    @Column(name="effort", nullable = false)
    private Integer effort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_session_id")
    private TrainingSession session;

    public TrainingSessionResult() {}

    public TrainingSessionResult(Integer effort, TrainingSession session) {
        this.effort = effort;
        this.session = session;
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
