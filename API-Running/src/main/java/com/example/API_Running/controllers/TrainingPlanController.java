package com.example.API_Running.controllers;

import com.example.API_Running.dtos.CreateTrainingPlanDTO;
import com.example.API_Running.services.TrainingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/trainingplan")
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
}
