package com.example.API_Running.services;

import com.example.API_Running.dtos.TrainerDTO;
import com.example.API_Running.dtos.UpdateTrainerDTO;
import com.example.API_Running.dtos.UserZonesDTO;
import com.example.API_Running.models.Runner;
import com.example.API_Running.models.Trainer;
import com.example.API_Running.models.TrainingPlan;
import com.example.API_Running.models.User;
import com.example.API_Running.repository.TrainerRepository;
import com.example.API_Running.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;

    @Autowired
    public TrainerService (TrainerRepository trainerRepository, UserRepository userRepository) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> updateTrainer(Long trainerId, UpdateTrainerDTO updateTrainerDTO) {
        HashMap<String,Object> data = new HashMap<>();
        Optional<Trainer> query = this.trainerRepository.findById(trainerId);
        if (query.isPresent()) {
            Trainer trainer = query.get();
            Optional<User> username_query = this.userRepository.findByUsername(updateTrainerDTO.getUsername());
            if (username_query.isPresent() && !Objects.equals(username_query.get().getId(), trainerId)) {
                data.put("error", "There's another user with this username");
                return new ResponseEntity<>(data, HttpStatus.CONFLICT);
            }
            Optional<User> mail_query = this.userRepository.findByMail(updateTrainerDTO.getMail());
            if (mail_query.isPresent() && !Objects.equals(mail_query.get().getId(), trainerId)) {
                data.put("error", "There's another user with this mail");
                return new ResponseEntity<>(data, HttpStatus.CONFLICT);
            }
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

    public ResponseEntity<Object> getTrainerStats(Long trainerId) {
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
        int usersEnrolled = 0;
        List<TrainingPlan> plans = trainer.getPlans();
        for (TrainingPlan plan : plans) {
            usersEnrolled += plan.getTrainingProgresses().size();
        }
        data.put("data", usersEnrolled);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
