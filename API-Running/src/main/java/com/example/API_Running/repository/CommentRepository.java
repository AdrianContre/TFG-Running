package com.example.API_Running.repository;

import com.example.API_Running.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.trainingWeek.id = :trainingWeekId ORDER BY c.sendDate ASC")
    List<Comment> findCommentsByTrainingWeekId(@Param("trainingWeekId") Long trainingWeekId);
}
