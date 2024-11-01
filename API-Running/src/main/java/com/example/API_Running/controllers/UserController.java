package com.example.API_Running.controllers;


import com.example.API_Running.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping(path="/{userId}")
    public ResponseEntity<Object> uploadProfilePicture(@PathVariable Long userId, @RequestParam MultipartFile profilePicture) {
        return this.userService.uploadProfilePicture(userId, profilePicture);
    }
}
