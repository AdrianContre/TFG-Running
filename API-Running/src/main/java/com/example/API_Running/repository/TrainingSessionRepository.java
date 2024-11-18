package com.example.API_Running.repository;

import com.example.API_Running.models.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession,Long> {
}
