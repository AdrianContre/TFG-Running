package com.example.API_Running.repository;

import com.example.API_Running.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Long> {

    @Query("SELECT a FROM Activity a WHERE a.runner.id = :runnerId")
    List<Activity> findActivitiesByRunnerId(@Param("runnerId") Long runnerId);

}
