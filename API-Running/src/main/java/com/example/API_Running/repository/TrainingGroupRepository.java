package com.example.API_Running.repository;

import com.example.API_Running.models.TrainingGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainingGroupRepository extends JpaRepository<TrainingGroup, Long> {
    Optional<TrainingGroup> findByName(String name);
}
