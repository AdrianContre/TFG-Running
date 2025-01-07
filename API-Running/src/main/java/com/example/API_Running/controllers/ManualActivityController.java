package com.example.API_Running.controllers;

import com.example.API_Running.dtos.CreateManualActivityDTO;
import com.example.API_Running.dtos.ModifyManualActivityDTO;
import com.example.API_Running.services.ManualActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @DeleteMapping(path="/{manualActivityId}")
    public ResponseEntity<Object> deleteManualActivity (@PathVariable Long manualActivityId) {
        return this.manualActivityService.deleteManualActivity(manualActivityId);
    }

    @PutMapping(path="/route/{manualActivityId}", consumes = "multipart/form-data")
    public ResponseEntity<Object> setRouteToManualActivity(@PathVariable Long manualActivityId, @RequestParam MultipartFile route) {
        return this.manualActivityService.uploadRoute(manualActivityId, route);
    }

    @GetMapping(path = "/{manualActId}")
    public ResponseEntity<Object> getManualActivity (@PathVariable Long manualActId) {
        return this.manualActivityService.getManualActivity(manualActId);
    }
    @PutMapping(path = "/{manualActivityId}")
    public ResponseEntity<Object> updateManualActivity (@PathVariable Long manualActivityId, @RequestBody ModifyManualActivityDTO modifyManualActivity) {
        return this.manualActivityService.updateManualActivity(manualActivityId, modifyManualActivity);
    }





}
