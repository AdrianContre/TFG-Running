package com.example.API_Running.controllers;

import com.example.API_Running.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/activities")
public class ActivityController {
    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping(path = "/runners/{runnerId}")
    public ResponseEntity<Object> getUserManualActivities(@PathVariable Long runnerId) {
//        return this.activityService.getUserManualActivities(runnerId);
        return this.activityService.getUserActivities(runnerId);
    }
}
