package com.example.API_Running.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path="api/v1/auth")
public class AuthController {

    @PostMapping(path="/login")
    public ResponseEntity<Object> login () {
        return ResponseEntity.ok("hola");
    }

    @PostMapping(path="/register")
    public ResponseEntity<Object> register () {
        return ResponseEntity.ok("hola");
    }
}
