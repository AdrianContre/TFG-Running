package com.example.API_Running.services;

import com.example.API_Running.dtos.LoginRequest;
import com.example.API_Running.dtos.RegisterRequest;
import com.example.API_Running.models.Runner;
import com.example.API_Running.models.Trainer;
import com.example.API_Running.models.User;
import com.example.API_Running.models.UserDetailsImplementation;
import com.example.API_Running.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService (UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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

        Optional<User> query = this.userRepository.findByUsername(username);
        if (query.isPresent()) {
            data.put("error", "User with this username already exists");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.BAD_REQUEST
            );
        }

        Optional<User> query2 = this.userRepository.findByMail(mail);
        if (query2.isPresent()) {
            data.put("error", "User with this mail already exists");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.BAD_REQUEST
            );
        }

        User savedUser;

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordEncoded = passwordEncoder.encode(password);
        if (isTrainer) {
            Trainer trainer = new Trainer();
            trainer.setExperience(request.getExperience());
            trainer.setMail(mail);
            trainer.setName(name);
            trainer.setSurname(surname);
            trainer.setUsername(username);
            trainer.setPassword(passwordEncoded);
            trainer.setWeight(weight);
            trainer.setHeight(height);
            trainer.setFcMax(fcMax);
            trainer.setIsTrainer(true);
            savedUser = userRepository.save(trainer);
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
            runner.setIsTrainer(false);
            savedUser = userRepository.save(runner);
        }
        UserDetailsImplementation u = new UserDetailsImplementation(savedUser);
        String token = jwtService.getToken(u);
        data.put("token", token);

        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> login (LoginRequest request) {
        HashMap<String,Object> data = new HashMap<>();
        Optional<User> query = this.userRepository.findByUsername(request.getUsername());
        if (!query.isPresent()) {
            data.put ("error", "User not found");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            data.put("error", "Incorrect password");
            return new ResponseEntity<>(data, HttpStatus.UNAUTHORIZED);
        }
        User u = query.get();
        UserDetailsImplementation user = new UserDetailsImplementation(u);
        String token=jwtService.getToken(user);
        data.put("token", token);
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }
}
