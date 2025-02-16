package com.example.API_Running.controllers;

import com.example.API_Running.dtos.AuthPolarDTO;
import com.example.API_Running.services.PolarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path="api/v1/polar")
public class PolarController {

    private final PolarService polarService;

    @Autowired
    public PolarController (PolarService polarService) {
        this.polarService = polarService;
    }

    @PostMapping(path = "/auth")
    public ResponseEntity<Object> authenticate (@RequestBody AuthPolarDTO authPolarDTO) {
        return this.polarService.authenticate(authPolarDTO);
    }
}
