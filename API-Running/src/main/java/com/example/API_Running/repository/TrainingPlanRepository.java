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

    @Query("SELECT tp FROM TrainingPlan tp " +
            "LEFT JOIN tp.groups tg " +
            "LEFT JOIN tp.trainingProgresses tpProg " +
            "WHERE tp.creator.id <> :userId " +
            "AND (tg IS NULL OR tp.groups IS EMPTY) " +
            "AND (tpProg.runner.id <> :userId OR tpProg IS NULL)")
    List<TrainingPlan> findAvailableTrainingPlans(@Param("userId") Long userId);

}
