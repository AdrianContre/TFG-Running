package com.example.API_Running.services;

import com.example.API_Running.dtos.CreateTrainingPlanDTO;
import com.example.API_Running.dtos.ListPlansDTO;
import com.example.API_Running.dtos.SessionDTO;
import com.example.API_Running.models.*;
import com.example.API_Running.repository.TrainerRepository;
import com.example.API_Running.repository.TrainingPlanRepository;
import com.example.API_Running.repository.TrainingSessionRepository;
import com.example.API_Running.repository.TrainingWeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrainingPlanService {

    private final TrainingPlanRepository trainingPlanRepository;
    private final TrainingWeekRepository trainingWeekRepository;
    private final TrainingSessionRepository trainingSessionRepository;
    private final TrainerRepository trainerRepository;

    @Autowired
    public TrainingPlanService(TrainingPlanRepository trainingPlanRepository, TrainingWeekRepository trainingWeekRepository, TrainingSessionRepository trainingSessionRepository, TrainerRepository trainerRepository) {
        this.trainingPlanRepository = trainingPlanRepository;
        this.trainingWeekRepository = trainingWeekRepository;
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainerRepository = trainerRepository;
    }

    public ResponseEntity<Object> createTrainingPlan(CreateTrainingPlanDTO trainingPlanDTO) {
        HashMap<String, Object> data = new HashMap<>();
        Long trainerId = trainingPlanDTO.getTrainerId();
        Optional<Trainer> query = this.trainerRepository.findById(trainerId);
        if (!query.isPresent()) {
            data.put("error", "Trainer not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Trainer trainer = query.get();
        String name = trainingPlanDTO.getName();
        String description = trainingPlanDTO.getDescription();
        Integer weeks = trainingPlanDTO.getNumWeeks();
        String objDistance = trainingPlanDTO.getObjDistance();
        String level = trainingPlanDTO.getLevel();
        List<TrainingWeek> trainingWeekList = new ArrayList<>();
        List<TrainingProgress> trainingProgressesList = new ArrayList<>();
        TrainingPlan trainingPlan = new TrainingPlan(name, description, weeks, objDistance, level, trainer, trainingWeekList, trainingProgressesList);
        trainingPlan.setGroups(new HashSet<>());
        TrainingPlan savedTrainingPlan = this.trainingPlanRepository.save(trainingPlan);

        List<List<SessionDTO>> sessionsDTO = trainingPlanDTO.getSessions();

        for (int i = 0; i < weeks; ++i) {
            TrainingWeek trainingWeek = new TrainingWeek();
            trainingWeek.setTrainingPlan(savedTrainingPlan);
            trainingWeek.setComments(new ArrayList<>());


            TrainingWeek savedWeek = this.trainingWeekRepository.save(trainingWeek);

            for (int j = 0; j < 7; ++j) {
                SessionDTO sessionDTO = sessionsDTO.get(i).get(j);
                if (sessionDTO != null) {
                    String sessionName = sessionDTO.getName();
                    String sessionDescription = sessionDTO.getDescription();
                    String type = sessionDTO.getType();

                    TrainingSession savedSession;
                    if (type.equals("running")) {
                        RunningSession runningSession = new RunningSession(sessionDTO.getRunningType(), sessionDTO.getDistance(), sessionDTO.getDuration());
                        runningSession.setName(sessionName);
                        runningSession.setDescription(sessionDescription);
                        runningSession.setTrainingWeek(savedWeek);
                        runningSession.setResults(new ArrayList<>());
                        savedSession = this.trainingSessionRepository.save(runningSession);
                    } else if (type.equals("strength")) {
                        StrengthSession strengthSession = new StrengthSession();
                        strengthSession.setName(sessionName);
                        strengthSession.setDescription(sessionDescription);
                        strengthSession.setTrainingWeek(savedWeek);
                        strengthSession.setResults(new ArrayList<>());
                        savedSession = this.trainingSessionRepository.save(strengthSession);
                    } else {
                        MobilitySession mobilitySession = new MobilitySession();
                        mobilitySession.setName(sessionName);
                        mobilitySession.setDescription(sessionDescription);
                        mobilitySession.setTrainingWeek(savedWeek);
                        mobilitySession.setResults(new ArrayList<>());
                        savedSession = this.trainingSessionRepository.save(mobilitySession);
                    }
                    savedWeek.getSessions().add(savedSession);
                } else {
                    savedWeek.getSessions().add(null);
                }
            }
            trainingWeekList.add(savedWeek);
        }


        trainer.getPlans().add(savedTrainingPlan);
        this.trainerRepository.save(trainer);

        data.put("data", "Training plan created properly");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


    public ResponseEntity<Object> getTrainingPlans() {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User user = u.getUser();
        Long userId = user.getId();

        List<TrainingPlan> plans = this.trainingPlanRepository.findAllByTrainerIdNot(userId);
        List<ListPlansDTO> plansDTO = new ArrayList<>();
        plans.stream().forEach(plan -> {
            ListPlansDTO p = new ListPlansDTO(plan);
            plansDTO.add(p);
        });
        data.put("data", plansDTO);
        return new ResponseEntity<>(data,HttpStatus.OK);

    }

    public ResponseEntity<Object> getTrainerPlans(Long trainerId) {
        HashMap<String,Object> data = new HashMap<>();
        Optional<Trainer> query = this.trainerRepository.findById(trainerId);
        if (!query.isPresent()) {
            data.put("error", "Trainer not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        List<TrainingPlan> plans = this.trainingPlanRepository.findAllByTrainerId(trainerId);
        List<ListPlansDTO> plansDTOS = new ArrayList<>();
        plans.stream().forEach(plan -> {
            ListPlansDTO p = new ListPlansDTO(plan);
            plansDTOS.add(p);
        });
        data.put("data", plansDTOS);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
