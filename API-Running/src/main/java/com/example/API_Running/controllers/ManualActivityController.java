package com.example.API_Running.controllers;

import com.example.API_Running.dtos.CreateManualActivityDTO;
import com.example.API_Running.services.ManualActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path="api/v1/manualactivities")
public class ManualActivityController {

    private final ManualActivityService manualActivityService;

    @Autowired
    public ManualActivityController (ManualActivityService manualActivityService) {
        this.manualActivityService = manualActivityService;
    }

    @PostMapping
    public ResponseEntity<Object> createManualActivity (@RequestBody CreateManualActivityDTO createManualActivityDTO) {
        return this.manualActivityService.createManualActivity(createManualActivityDTO);
    }
}
