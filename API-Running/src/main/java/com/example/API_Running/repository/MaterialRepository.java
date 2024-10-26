package com.example.API_Running.repository;

import com.example.API_Running.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material,Long> {
    @Query("SELECT m FROM Material m WHERE m.runner.id = :runnerId")
    List<Material> findMaterialsByRunnerId(@Param("runnerId") Long runnerId);
}
