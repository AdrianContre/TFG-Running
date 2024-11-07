package com.example.API_Running.repository;

import com.example.API_Running.models.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlan,Long> {
}
