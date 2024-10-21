package com.example.API_Running.services;

import com.example.API_Running.dtos.RegisterRequest;
import com.example.API_Running.models.Runner;
import com.example.API_Running.models.Trainer;
import com.example.API_Running.models.User;
import com.example.API_Running.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    AuthService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> register (RegisterRequest request) {
        HashMap<String,Object> data = new HashMap<>();
        String name = request.getName();
        String surname = request.getSurname();
        String username = request.getUsername();
        String mail = request.getMail();
        String password = request.getPassword();
        Integer weight = request.getWeight();
        Integer height = request.getHeight();
        Integer fcMax = request.getFcMax();
        boolean isTrainer = request.isTrainer();

        if (name == null || surname == null || username == null || mail == null || password == null || weight == null || height == null || fcMax == null) {
            return new ResponseEntity<>(
                    data.put("error", "All parameters are needed"),
                    HttpStatus.BAD_REQUEST
            );
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncoded = passwordEncoder.encode(password);

        Runner runner = new Runner();
        String token = passwordEncoder.encode("token_encrypted31"+username);

        runner.setName(name);
        runner.setSurname(surname);
        runner.setMail(mail);
        runner.setUsername(username);
        runner.setPassword(passwordEncoded);
        runner.setHeight(height);
        runner.setWeight(weight);
        runner.setFcMax(fcMax);
        runner.setToken(token);
        runner.setIsTrainer(isTrainer);
        if (isTrainer) {
            Trainer trainer = new Trainer();
            trainer.setExperience(request.getExperience());
            trainer.setRunner(runner);
            runner.setTrainerProfile(trainer);
        }

        userRepository.save(runner);

        data.put("token", token);

        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }
}
