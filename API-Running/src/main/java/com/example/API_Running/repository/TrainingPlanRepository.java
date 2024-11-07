package com.example.API_Running.repository;

import com.example.API_Running.models.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlan,Long> {
    @Query("SELECT tp FROM TrainingPlan tp WHERE tp.creator.id <> :userId")
    List<TrainingPlan> findAllByTrainerIdNot(@Param("userId") Long userId);

    @Query("SELECT tp FROM TrainingPlan tp WHERE tp.creator.id = :trainerId")
    List<TrainingPlan> findAllByTrainerId(@Param("trainerId") Long trainerId);
}
