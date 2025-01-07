package com.example.API_Running.controllers;

import com.example.API_Running.dtos.CreateCommentDTO;
import com.example.API_Running.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(path = "/trainingweeks/{trainingWeekId}")
    public ResponseEntity<Object> createComment(@PathVariable Long trainingWeekId,@RequestBody CreateCommentDTO createCommentDTO) {
        return this.commentService.createComment(trainingWeekId, createCommentDTO);
    }

    @GetMapping(path = "/trainingweeks/{trainingWeekId}")
    public ResponseEntity<Object> getTrainingWeekComments(@PathVariable Long trainingWeekId) {
        return this.commentService.getTrainingWeekComments(trainingWeekId);
    }
}
