package com.example.API_Running.repository;

import com.example.API_Running.models.TrainingSession;
import com.example.API_Running.models.TrainingSessionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrainingSessionResultRepository extends JpaRepository<TrainingSessionResult, Long> {

    @Query("SELECT tsr FROM TrainingSessionResult tsr WHERE tsr.session.id = :trainingSessionId AND tsr.runner.id = :userId")
    Optional<TrainingSessionResult> existsResultForUserAndSession(@Param("trainingSessionId")Long trainingSessionId, @Param("userId") Long userId);


    @Query("SELECT tsr FROM TrainingSessionResult tsr WHERE tsr.session.trainingWeek.trainingPlan.id = :planId AND tsr.runner.id = :runnerId")
    List<TrainingSessionResult> findAllByPlanAndRunner(@Param("planId")Long planId, @Param("runnerId")Long runnerId);

    @Query("SELECT tsr from TrainingSessionResult tsr where tsr.session.trainingWeek.trainingPlan.id = :planId")
    List<TrainingSessionResult> findAllByPlan(@Param("planId") Long planId);
}
