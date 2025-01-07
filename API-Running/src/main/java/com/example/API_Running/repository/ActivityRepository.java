package com.example.API_Running.repository;

import com.example.API_Running.models.Activity;
import com.example.API_Running.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Long> {

    @Query("SELECT a FROM Activity a WHERE a.runner.id = :runnerId")
    List<Activity> findActivitiesByRunnerId(@Param("runnerId") Long runnerId);

    List<Activity> findAllByMaterialsContains(Material material);

    List<Activity> findByRunnerIdAndDateBetween(Long runnerId, LocalDateTime startDate, LocalDateTime endDate);




}
