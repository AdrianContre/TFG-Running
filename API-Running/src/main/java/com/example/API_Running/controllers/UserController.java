package com.example.API_Running.controllers;


import com.example.API_Running.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path="api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path="/whoAmI")
    public ResponseEntity<Object> getAuthUser () {
        return this.userService.whoAmI();
    }
}
