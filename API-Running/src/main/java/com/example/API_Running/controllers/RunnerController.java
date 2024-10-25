package com.example.API_Running.controllers;

import com.example.API_Running.dtos.UpdateRunnerDTO;
import com.example.API_Running.services.RunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path="api/v1/runners")
public class RunnerController {

    private final RunnerService runnerService;

    @Autowired
    public RunnerController (RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @PutMapping(path="/{runnerId}")
    public ResponseEntity<Object> updateRunner (@PathVariable Long runnerId, @RequestBody UpdateRunnerDTO updateRunnerDTO) {
        return this.runnerService.updateRunner(runnerId, updateRunnerDTO);
    }
}
