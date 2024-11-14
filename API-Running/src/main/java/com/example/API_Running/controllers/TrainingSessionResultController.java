package com.example.API_Running.controllers;


import com.example.API_Running.dtos.CreateMobilitySessionResultDTO;
import com.example.API_Running.dtos.CreateRunningSessionResultDTO;
import com.example.API_Running.dtos.CreateStrengthSessionResultDTO;
import com.example.API_Running.services.TrainingSessionResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(path="/running")
    public ResponseEntity<Object> createRunningSessionResult(@RequestBody CreateRunningSessionResultDTO createRunningSessionResultDTO) {
        return this.trainingSessionResultService.createRunningSessionResult(createRunningSessionResultDTO);
    }

    @PutMapping(path="/{trainingSessionResultId}/route")
    public ResponseEntity<Object> addRouteToRunningSessionResult(@PathVariable Long trainingSessionResultId, @RequestParam MultipartFile route) {
        return this.trainingSessionResultService.addRouteToRunningSessionResult(trainingSessionResultId, route);
    }

    @PostMapping(path = "/strength")
    public ResponseEntity<Object> createStrengthSessionResult(@RequestBody CreateStrengthSessionResultDTO createStrengthSessionResult) {
        return this.trainingSessionResultService.createStrengthSessionResult(createStrengthSessionResult);
    }

    @PostMapping(path= "/mobility")
    public ResponseEntity<Object> createMobilitySessionResult(@RequestBody CreateMobilitySessionResultDTO createMobilitySessionResultDTO) {
        return this.trainingSessionResultService.createMobilitySessionResult(createMobilitySessionResultDTO);
    }
}
