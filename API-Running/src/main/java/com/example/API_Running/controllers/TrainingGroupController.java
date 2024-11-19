package com.example.API_Running.controllers;


import com.example.API_Running.dtos.CreateTrainingGroupDTO;
import com.example.API_Running.services.TrainingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path="api/v1/groups")
public class TrainingGroupController {
    private final TrainingGroupService trainingGroupService;

    @Autowired
    public TrainingGroupController(TrainingGroupService trainingGroupService) {
        this.trainingGroupService = trainingGroupService;
    }

    @PostMapping
    public ResponseEntity<Object> createTrainingGroup(@RequestBody CreateTrainingGroupDTO trainingGroupDTO) {
        return this.trainingGroupService.createGroup(trainingGroupDTO);
    }

    @GetMapping(path="/available")
    public ResponseEntity<Object> getAvailableGroups() {
        return this.trainingGroupService.getAvailableGroups();
    }

    @GetMapping(path="/trainers/{trainerId}")
    public ResponseEntity<Object> getTrainerGroups(@PathVariable Long trainerId) {
        return this.trainingGroupService.getTrainerGroups(trainerId);
    }
}

