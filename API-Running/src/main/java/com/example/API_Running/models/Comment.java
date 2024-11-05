package com.example.API_Running.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="content", nullable = false)
    private String content;

    @CreationTimestamp
    @Column(name="sendDate", nullable = false)
    private LocalDateTime sendDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_week_id")
    private TrainingWeek trainingWeek;

    public Comment() {}

    public Comment(Long id, String content, LocalDateTime sendDate, User sender, TrainingWeek trainingWeek) {
        this.id = id;
        this.content = content;
        this.sendDate = sendDate;
        this.sender = sender;
        this.trainingWeek = trainingWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public TrainingWeek getTrainingWeek() {
        return trainingWeek;
    }

    public void setTrainingWeek(TrainingWeek trainingWeek) {
        this.trainingWeek = trainingWeek;
    }
}
