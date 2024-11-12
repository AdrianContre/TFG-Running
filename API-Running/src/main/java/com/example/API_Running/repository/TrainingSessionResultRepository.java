package com.example.API_Running.repository;

import com.example.API_Running.models.TrainingSessionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TrainingSessionResultRepository extends JpaRepository<TrainingSessionResult, Long> {

    @Query("SELECT tsr FROM TrainingSessionResult tsr WHERE tsr.session.id = :trainingSessionId AND tsr.runner.id = :userId")
    Optional<TrainingSessionResult> existsResultForUserAndSession(Long trainingSessionId, Long userId);
}
