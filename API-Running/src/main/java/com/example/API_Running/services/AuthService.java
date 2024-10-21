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
    private final JwtService jwtService;

    @Autowired
    AuthService (UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
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
        boolean isTrainer = request.getTrainer();


        if (name == null || surname == null || username == null || mail == null || password == null || weight == null || height == null || fcMax == null) {
            data.put("error", "All parameters are needed");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.BAD_REQUEST
            );
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncoded = passwordEncoder.encode(password);
        //String token = passwordEncoder.encode("token_encrypted31"+username);
        String token = jwtService.generateToken(username);
        if (isTrainer) {
            Trainer trainer = new Trainer();
            trainer.setExperience(request.getExperience());
            trainer.setMail(mail);
            trainer.setName(name);
            trainer.setSurname(surname);
            trainer.setUsername(username);
            trainer.setPassword(passwordEncoded);
            trainer.setToken(token);
            trainer.setWeight(weight);
            trainer.setHeight(height);
            trainer.setFcMax(fcMax);
            trainer.setIsTrainer(true);
            userRepository.save(trainer);
        }
        else {
            Runner runner = new Runner();
            runner.setName(name);
            runner.setSurname(surname);
            runner.setMail(mail);
            runner.setUsername(username);
            runner.setPassword(passwordEncoded);
            runner.setHeight(height);
            runner.setWeight(weight);
            runner.setFcMax(fcMax);
            runner.setToken(token);
            runner.setIsTrainer(false);
            userRepository.save(runner);
        }








        data.put("token", token);

        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }
}
