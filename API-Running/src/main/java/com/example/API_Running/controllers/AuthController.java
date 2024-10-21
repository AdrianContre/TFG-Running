package com.example.API_Running.controllers;

import com.example.API_Running.dtos.RegisterRequest;
import com.example.API_Running.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path="api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    AuthController (AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path="/login")
    public ResponseEntity<Object> login () {
        return ResponseEntity.ok("hola");
    }

    @PostMapping(path="/register")
    public ResponseEntity<Object> register (@RequestBody RegisterRequest request) {
        return this.authService.register(request);
    }
}
