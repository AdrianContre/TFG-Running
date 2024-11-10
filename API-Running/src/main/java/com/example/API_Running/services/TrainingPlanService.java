package com.example.API_Running.services;

import com.example.API_Running.dtos.*;
import com.example.API_Running.models.*;
import com.example.API_Running.repository.*;
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
    private final RunnerRepository runnerRepository;
    private final TrainingProgressRepository trainingProgressRepository;

    @Autowired
    public TrainingPlanService(TrainingPlanRepository trainingPlanRepository, TrainingWeekRepository trainingWeekRepository, TrainingSessionRepository trainingSessionRepository, TrainerRepository trainerRepository, RunnerRepository runnerRepository, TrainingProgressRepository trainingProgressRepository) {
        this.trainingPlanRepository = trainingPlanRepository;
        this.trainingWeekRepository = trainingWeekRepository;
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainerRepository = trainerRepository;
        this.runnerRepository = runnerRepository;
        this.trainingProgressRepository = trainingProgressRepository;
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

        //List<TrainingPlan> plans = this.trainingPlanRepository.findAllByTrainerIdNot(userId);
        List<TrainingPlan> plans = this.trainingPlanRepository.findAvailableTrainingPlans(userId);
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

    public ResponseEntity<Object> getPlan(Long planId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User user = u.getUser();
        Long userId = user.getId();
        HashMap<String, Object> data = new HashMap<>();
        Optional<TrainingPlan> query = this.trainingPlanRepository.findById(planId);
        if (!query.isPresent()) {
            data.put("error", "Training plan not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        TrainingPlan plan = query.get();
        boolean enrolled = false;
        Optional<TrainingProgress> query2 = this.trainingProgressRepository.findProgressByPlanAndRunner(userId, planId);
        if (query2.isPresent()) {
            enrolled = true;
        }
        TrainingPlanDetailDTO info = new TrainingPlanDetailDTO(plan,enrolled);
        data.put("data", info);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> enrollUserToAPlan(Long planId, EnrrollToAPlanDTO body) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Runner> query = this.runnerRepository.findById(body.getUserId());
        if (!query.isPresent()) {
            data.put("error", "User not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        Optional<TrainingPlan> query2 = this.trainingPlanRepository.findById(planId);
        if (!query2.isPresent()) {
            data.put("error", "Training plan not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        TrainingPlan t = query2.get();
        Runner r = query.get();
        Optional<TrainingProgress> query_tp = this.trainingProgressRepository.findProgressByPlanAndRunner(r.getId(), planId);
        if (query_tp.isPresent()) {
            data.put("error", "The user has already a progress on this plan");
            return new ResponseEntity<>(data, HttpStatus.CONFLICT);
        }
        TrainingProgress newTp = new TrainingProgress();
        newTp.setPercentage((float) 0);
        newTp.setRunner(r);
        newTp.setTrainingPlan(t);
        TrainingProgress savedTp = this.trainingProgressRepository.save(newTp);
        t.addTrainningProgress(savedTp);
        this.trainingPlanRepository.save(t);
        data.put("data", "User enrolled to the plan properly");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> getRunnerEnrolledPlans(Long runnerId) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Runner> query = this.runnerRepository.findById(runnerId);
        if (!query.isPresent()) {
            data.put("error", "User not found");
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        List<TrainingProgress> tps = this.trainingProgressRepository.findAllUserProgress(runnerId);
        if (tps.isEmpty()) {
            data.put("data", new ArrayList<>());
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        List<EnrolledPlanDTO> info = new ArrayList<>();
        tps.stream().forEach(tp -> {
            TrainingPlan trainingPlan = tp.getTrainingPlan();
            EnrolledPlanDTO planDTO = new EnrolledPlanDTO(trainingPlan, tp.getPercentage());
            info.add(planDTO);
        });
        data.put("data", info);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> withdrawUserInPlan(Long planId, EnrrollToAPlanDTO body) {
        HashMap<String, Object> data = new HashMap<>();
        Long userId = body.getUserId();
        Optional<Runner> query = this.runnerRepository.findById(userId);
        if (!query.isPresent()) {
            data.put("error", "Runner not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        Optional<TrainingProgress> query2 = this.trainingProgressRepository.findProgressByPlanAndRunner(userId, planId);
        if (!query2.isPresent()) {
            data.put("error", "The runner is not enrolled to the plan");
        }
        TrainingProgress tp = query2.get();
        this.trainingProgressRepository.delete(tp);
        data.put("data", "User unenrolled properly");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
