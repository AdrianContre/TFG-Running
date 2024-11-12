package com.example.API_Running.controllers;


import com.example.API_Running.services.TrainingSessionResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/sessionresults")
public class TrainingSessionResultController {

    private final TrainingSessionResultService trainingSessionResultService;

    @Autowired
    public TrainingSessionResultController(TrainingSessionResultService trainingSessionResultService) {
        this.trainingSessionResultService = trainingSessionResultService;
    }

    @GetMapping(path="/users/{userId}/plans/{planId}")
    public ResponseEntity<Object> getHasResultsUserInAPlan(@PathVariable Long userId, @PathVariable Long planId) {
        return this.trainingSessionResultService.getHasResultsUserInAPlan(userId, planId);
    }
}
