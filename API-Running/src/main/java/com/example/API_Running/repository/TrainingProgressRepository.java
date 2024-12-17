package com.example.API_Running.repository;

import com.example.API_Running.models.TrainingProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrainingProgressRepository extends JpaRepository<TrainingProgress, Long> {
    @Query("SELECT tp FROM TrainingProgress tp WHERE tp.runner.id = :userId and tp.trainingPlan.id = :planId")
    Optional<TrainingProgress> findProgressByPlanAndRunner(@Param("userId") Long userId, @Param("planId") Long planId);

    @Query("SELECT tp FROM TrainingProgress tp WHERE tp.trainingPlan.id = :planId")
    List<TrainingProgress> findProgressesByPlanId(@Param("planId") Long planId);

    @Query("SELECT tp FROM TrainingProgress tp WHERE tp.runner.id = :userId")
    List<TrainingProgress> findAllUserProgress(@Param("userId") Long userId);
}
