package com.example.API_Running.repository;

import com.example.API_Running.models.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer,Long> {
    Optional<Trainer> findByUsername (String username);
}
