package com.example.API_Running.repository;

import com.example.API_Running.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity,Long> {

}
