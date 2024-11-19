package com.example.API_Running.services;

import com.example.API_Running.dtos.CreateTrainingGroupDTO;
import com.example.API_Running.models.Runner;
import com.example.API_Running.models.Trainer;
import com.example.API_Running.models.TrainingGroup;
import com.example.API_Running.repository.RunnerRepository;
import com.example.API_Running.repository.TrainerRepository;
import com.example.API_Running.repository.TrainingGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrainingGroupService {

    private final TrainingGroupRepository trainingGroupRepository;
    private final TrainerRepository trainerRepository;
    private final RunnerRepository runnerRepository;

    @Autowired
    public TrainingGroupService(TrainingGroupRepository trainingGroupRepository, TrainerRepository trainerRepository, RunnerRepository runnerRepository) {
        this.trainingGroupRepository = trainingGroupRepository;
        this.trainerRepository = trainerRepository;
        this.runnerRepository = runnerRepository;
    }

    public ResponseEntity<Object> createGroup(CreateTrainingGroupDTO trainingGroupDTO) {
        HashMap<String, Object> data = new HashMap<>();
        String name = trainingGroupDTO.getName();
        Optional<TrainingGroup> query = this.trainingGroupRepository.findByName(name);
        if (query.isPresent()) {
            data.put("error", "Group with this name already exists");
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }
        Long trainerId = trainingGroupDTO.getTrainerId();
        Optional<Trainer> trainer_query = this.trainerRepository.findById(trainerId);
        if (!trainer_query.isPresent()) {
            data.put("error", "Trainer does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        List<Long> runnersId = trainingGroupDTO.getRunnersId();
        Set<Runner> runnerSet = new HashSet<>();
        Trainer trainer = trainer_query.get();
        TrainingGroup group = new TrainingGroup(name, trainer, new HashSet<>(), new HashSet<>());
        TrainingGroup savedGroup = this.trainingGroupRepository.save(group);
        for (int i = 0; i < runnersId.size(); ++i) {
            Optional<Runner> runner_query = this.runnerRepository.findById(runnersId.get(i));
            if (!runner_query.isPresent()) {
                data.put("error", "There's a user that does not exist");
                return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
            }
            Runner runner = runner_query.get();
            runnerSet.add(runner);
            runner.addGroup(savedGroup);
            this.runnerRepository.save(runner);
        }
        trainer.addManagedGroups(savedGroup);
        savedGroup.setRunners(runnerSet);
        this.trainerRepository.save(trainer);
        this.trainingGroupRepository.save(savedGroup);
        data.put("data", "Group created successfully");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
