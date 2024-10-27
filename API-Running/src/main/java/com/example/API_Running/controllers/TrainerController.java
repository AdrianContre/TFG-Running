package com.example.API_Running.controllers;

import com.example.API_Running.dtos.UpdateTrainerDTO;
import com.example.API_Running.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path="api/v1/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    @Autowired
    public TrainerController (TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PutMapping(path="{trainerId}")
    public ResponseEntity<Object> updateTrainer (@PathVariable Long trainerId, @RequestBody UpdateTrainerDTO updateTrainerDTO) {
        return this.trainerService.updateTrainer(trainerId, updateTrainerDTO);
    }

    @GetMapping(path="/{trainerId}/zones")
    public ResponseEntity<Object> getZones (@PathVariable Long trainerId) {
        return this.trainerService.getZones(trainerId);
    }
}
