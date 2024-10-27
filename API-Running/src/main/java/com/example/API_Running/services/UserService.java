package com.example.API_Running.services;

import com.example.API_Running.dtos.RunnerDTO;
import com.example.API_Running.dtos.TrainerDTO;
import com.example.API_Running.models.Runner;
import com.example.API_Running.models.Trainer;
import com.example.API_Running.models.User;
import com.example.API_Running.models.UserDetailsImplementation;
import com.example.API_Running.repository.RunnerRepository;
import com.example.API_Running.repository.TrainerRepository;
import com.example.API_Running.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RunnerRepository runnerRepository;
    private final TrainerRepository trainerRepository;

    @Autowired
    public UserService (UserRepository userRepository, RunnerRepository runnerRepository, TrainerRepository trainerRepository) {
         this.userRepository = userRepository;
         this.runnerRepository = runnerRepository;
         this.trainerRepository = trainerRepository;
    }


    public ResponseEntity<Object> whoAmI() {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User user = u.getUser();
        if (user instanceof Trainer) {
            Optional<Trainer> query = this.trainerRepository.findByUsername(user.getUsername());
            Trainer trainer = query.get();
            TrainerDTO t = new TrainerDTO(trainer);
            data.put("userLogged",t);
        }
        else {
            Optional<Runner> query = this.runnerRepository.findByUsername(u.getUsername());
            Runner runner = query.get();
            RunnerDTO r = new RunnerDTO(runner);
            data.put("userLogged", r);
        }
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }
}
