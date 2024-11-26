package com.example.API_Running.repository;

import com.example.API_Running.models.Runner;
import com.example.API_Running.models.TrainingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrainingGroupRepository extends JpaRepository<TrainingGroup, Long> {
    Optional<TrainingGroup> findByName(String name);

    @Query("SELECT tg FROM TrainingGroup tg WHERE tg.trainer.id <> :userId AND :runner MEMBER OF tg.runners")
    List<TrainingGroup> findAllNotCreatorAndIncluded(@Param("userId") Long userId, @Param("runner") Runner runner);

    @Query("SELECT tg FROM TrainingGroup tg WHERE tg.trainer.id = :trainerId")
    List<TrainingGroup> findAllByCreator(@Param("trainerId") Long trainerId);
}
