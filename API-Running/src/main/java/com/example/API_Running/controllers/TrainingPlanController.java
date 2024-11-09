package com.example.API_Running.controllers;

import com.example.API_Running.dtos.CreateTrainingPlanDTO;
import com.example.API_Running.dtos.EnrrollToAPlanDTO;
import com.example.API_Running.services.TrainingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/trainingplans")
public class TrainingPlanController {
    private final TrainingPlanService trainingPlanService;

    @Autowired
    public TrainingPlanController(TrainingPlanService trainingPlanService) {
        this.trainingPlanService = trainingPlanService;
    }

    @PostMapping
    public ResponseEntity<Object> createTrainingPlan (@RequestBody CreateTrainingPlanDTO trainingPlanDTO) {
        return this.trainingPlanService.createTrainingPlan(trainingPlanDTO);
    }

    @GetMapping
    public ResponseEntity<Object> getTrainingPlans() {
        return this.trainingPlanService.getTrainingPlans();
    }

    @GetMapping(path = "/trainers/{trainerId}")
    public ResponseEntity<Object> getTrainersPlan(@PathVariable Long trainerId) {
        return this.trainingPlanService.getTrainerPlans(trainerId);
    }

    @GetMapping(path="/{planId}")
    public ResponseEntity<Object> getPlan(@PathVariable Long planId) {
        return this.trainingPlanService.getPlan(planId);
    }

    @PostMapping(path="/{planId}/enroll")
    public ResponseEntity<Object> enrollPlanToUser (@PathVariable Long planId, @RequestBody EnrrollToAPlanDTO body) {
        return this.trainingPlanService.enrollUserToAPlan(planId, body);
    }

    @GetMapping(path="/enrolled/{runnerId}")
    public ResponseEntity<Object> getRunnerEnrolledPlans(@PathVariable Long runnerId) {
        return this.trainingPlanService.getRunnerEnrolledPlans(runnerId);
    }
}
