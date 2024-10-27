package com.example.API_Running.services;

import com.example.API_Running.dtos.TrainerDTO;
import com.example.API_Running.dtos.UpdateTrainerDTO;
import com.example.API_Running.dtos.UserZonesDTO;
import com.example.API_Running.models.Runner;
import com.example.API_Running.models.Trainer;
import com.example.API_Running.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;

    @Autowired
    public TrainerService (TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public ResponseEntity<Object> updateTrainer(Long trainerId, UpdateTrainerDTO updateTrainerDTO) {
        HashMap<String,Object> data = new HashMap<>();
        Optional<Trainer> query = this.trainerRepository.findById(trainerId);
        if (query.isPresent()) {
            Trainer trainer = query.get();
            trainer.setName(updateTrainerDTO.getName());
            trainer.setSurname(updateTrainerDTO.getSurname());
            trainer.setUsername(updateTrainerDTO.getUsername());
            trainer.setMail(updateTrainerDTO.getMail());
            trainer.setHeight(updateTrainerDTO.getHeight());
            trainer.setWeight(updateTrainerDTO.getWeight());
            trainer.setFcMax(updateTrainerDTO.getFcMax());
            trainer.setExperience(updateTrainerDTO.getExperience());
            this.trainerRepository.save(trainer);
            TrainerDTO info = new TrainerDTO(trainer);
            data.put("data", info);
            return new ResponseEntity<>(
                    data,
                    HttpStatus.OK
            );
        }
        data.put("error", "Trainer with id "+ trainerId + " not found");
        return new ResponseEntity<>(
                data,
                HttpStatus.NOT_FOUND
        );
    }

    public ResponseEntity<Object> getZones(Long trainerId) {
        HashMap<String,Object> data = new HashMap<>();
        Optional<Trainer> query = this.trainerRepository.findById(trainerId);
        if (!query.isPresent()) {
            data.put("error", "User not found");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }
        Trainer trainer = query.get();
        ArrayList<Integer> zones =trainer.getZones();
        UserZonesDTO zonesDTO = new UserZonesDTO(zones);
        data.put("data", zonesDTO);
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }
}
