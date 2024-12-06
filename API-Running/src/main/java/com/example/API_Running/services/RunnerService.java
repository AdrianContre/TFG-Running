package com.example.API_Running.services;

import com.example.API_Running.dtos.RunnerDTO;
import com.example.API_Running.dtos.UpdateRunnerDTO;
import com.example.API_Running.dtos.UserZonesDTO;
import com.example.API_Running.models.Runner;
import com.example.API_Running.models.User;
import com.example.API_Running.repository.RunnerRepository;
import com.example.API_Running.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
public class RunnerService {

    private final RunnerRepository runnerRepository;
    private final UserRepository userRepository;

    public RunnerService (RunnerRepository runnerRepository, UserRepository userRepository) {
        this.runnerRepository = runnerRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> updateRunner(Long runnerId, UpdateRunnerDTO updateRunnerDTO) {
        HashMap<String,Object> data = new HashMap<>();
        Optional<Runner> query = this.runnerRepository.findById(runnerId);
        if (query.isPresent()) {
            Runner runner = query.get();
            Optional<User> username_query = this.userRepository.findByUsername(updateRunnerDTO.getUsername());
            if (username_query.isPresent() && !Objects.equals(username_query.get().getId(), runnerId)) {
                data.put("error", "There's another user with this username");
                return new ResponseEntity<>(data, HttpStatus.CONFLICT);
            }
            Optional<User> mail_query = this.userRepository.findByMail(updateRunnerDTO.getMail());
            if (mail_query.isPresent() && !Objects.equals(mail_query.get().getId(), runnerId)) {
                data.put("error", "There's another user with this mail");
                return new ResponseEntity<>(data, HttpStatus.CONFLICT);
            }
            runner.setName(updateRunnerDTO.getName());
            runner.setSurname(updateRunnerDTO.getSurname());
            runner.setUsername(updateRunnerDTO.getUsername());
            runner.setMail(updateRunnerDTO.getMail());
            runner.setHeight(updateRunnerDTO.getHeight());
            runner.setWeight(updateRunnerDTO.getWeight());
            runner.setFcMax(updateRunnerDTO.getFcMax());
            this.runnerRepository.save(runner);
            RunnerDTO info = new RunnerDTO(runner);
            data.put("data", info);
            return new ResponseEntity<>(
                    data,
                    HttpStatus.OK
            );
        }
        data.put("error", "Runner with id " + runnerId + " not found");
        return new ResponseEntity<>(
                data,
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<Object> getZones(Long runnerId) {
        HashMap<String,Object> data = new HashMap<>();
        Optional<Runner> query = this.runnerRepository.findById(runnerId);
        if (!query.isPresent()) {
            data.put("error", "User not found");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }
        Runner runner = query.get();
        ArrayList<Integer> zones = runner.getZones();
        UserZonesDTO zonesDTO = new UserZonesDTO(zones);
        data.put("data", zonesDTO);
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }
}
