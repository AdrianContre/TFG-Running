package com.example.API_Running.repository;

import com.example.API_Running.models.Runner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RunnerRepository extends JpaRepository<Runner,Long> {
    Optional<Runner> findByUsername(String username);
}
