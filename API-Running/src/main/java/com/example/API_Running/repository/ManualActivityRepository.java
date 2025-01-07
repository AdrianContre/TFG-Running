package com.example.API_Running.repository;

import com.example.API_Running.models.ManualActivity;
import com.example.API_Running.models.Runner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManualActivityRepository extends JpaRepository<ManualActivity,Long> {
    List<ManualActivity> findByRunner(Runner runner);
}
