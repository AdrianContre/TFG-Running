package com.example.API_Running.services;

import com.example.API_Running.dtos.*;
import com.example.API_Running.models.*;
import com.example.API_Running.repository.RunnerRepository;
import com.example.API_Running.repository.TrainerRepository;
import com.example.API_Running.repository.TrainingGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), trainingGroupDTO.getTrainerId())) {
            data.put("error", "You can not create group on behalf other user");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }

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
        String description = trainingGroupDTO.getDescription();
        TrainingGroup group = new TrainingGroup(name, description, trainer, new HashSet<>(), new HashSet<>());
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

    public ResponseEntity<Object> getAvailableGroups() {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        Runner runner = (Runner) userAuth;
        List<TrainingGroup> groups = this.trainingGroupRepository.findAllNotCreatorAndIncluded(userAuth.getId(), runner);
        List<GroupDTO> groupsDTO = new ArrayList<>();
        groups.forEach(group -> {
            String trainerName = group.getTrainer().getName() + " " + group.getTrainer().getSurname() + "(@" + group.getTrainer().getUsername() + ")";
            GroupDTO dto = new GroupDTO(group.getId(),group.getName(), trainerName);
            groupsDTO.add(dto);
        });
        data.put("data", groupsDTO);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> getTrainerGroups(Long trainerId) {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), trainerId)) {
            data.put("error", "You can not get other trainer plans");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        List<TrainingGroup> groups = this.trainingGroupRepository.findAllByCreator(trainerId);
        List<GroupDTO> groupsDTO = new ArrayList<>();
        groups.forEach(group -> {
            String trainerName = group.getTrainer().getName() + " " + group.getTrainer().getSurname() + "(@" + group.getTrainer().getUsername() + ")";
            GroupDTO dto = new GroupDTO(group.getId(), group.getName(), trainerName);
            groupsDTO.add(dto);
        });
        data.put("data", groupsDTO);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> getGroup(Long groupId) {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        Optional<TrainingGroup> query = this.trainingGroupRepository.findById(groupId);
        if (!query.isPresent()) {
            data.put("error", "The training group does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        TrainingGroup tg = query.get();
        if ((userAuth.getId() != tg.getTrainer().getId()) && !(tg.belongsUser(userAuth.getId()))) {
            data.put("error", "You are not the creator of the group or you do not belong to the group");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        String name = tg.getName();
        String description = tg.getDescription();
        Trainer trainer = tg.getTrainer();
        String trainerFullInfo = trainer.getName() + " " + trainer.getSurname() + "(@" + trainer.getUsername() + ")";
        Set<Runner> runners = tg.getRunners();
        List<UserDTO> runnersInfo = new ArrayList<>();
        runners.forEach(runner -> {
            UserDTO userDTO = new UserDTO(runner.getId(), runner.getName(), runner.getSurname(), runner.getUsername());
            runnersInfo.add(userDTO);
        });
        GroupDetailsDTO dto = new GroupDetailsDTO(tg.getId(), name, description,trainer.getId(), trainerFullInfo, runnersInfo);
        data.put("data", dto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> editGroup(Long groupId, EditGroupDTO editGroupDTO) {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        Optional<TrainingGroup> query = this.trainingGroupRepository.findById(groupId);
        if (!query.isPresent()) {
            data.put("error", "Group does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        TrainingGroup tg = query.get();
        if (!Objects.equals(tg.getTrainer().getId(), userAuth.getId())) {
            data.put("error", "You are not the creator of the group");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        tg.setName(editGroupDTO.getName());
        tg.setDescription(editGroupDTO.getDescription());
        List<Long> membersId = editGroupDTO.getMembersId();
        Set<Runner> members = new HashSet<>();
        List<Runner> runners = this.runnerRepository.findAllById(membersId);
        for (Runner runner : runners) {
            members.add(runner);
        }
        tg.setRunners(members);
        TrainingGroup savedTg = this.trainingGroupRepository.save(tg);
        data.put("data", "Edited properly");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteGroup(Long groupId) {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        Optional<TrainingGroup> query = this.trainingGroupRepository.findById(groupId);
        if (!query.isPresent()) {
            data.put("error", "Group does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        TrainingGroup tg = query.get();
        if (!Objects.equals(tg.getTrainer().getId(), userAuth.getId())) {
            data.put("error", "You are not the creator of the group");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        this.trainingGroupRepository.delete(tg);
        data.put("data", "Group deleted successfully");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
