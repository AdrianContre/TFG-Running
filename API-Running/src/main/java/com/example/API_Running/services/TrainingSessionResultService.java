package com.example.API_Running.services;

import com.example.API_Running.dtos.*;
import com.example.API_Running.models.*;
import com.example.API_Running.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class TrainingSessionResultService {
    private final TrainingSessionResultRepository trainingSessionResultRepository;
    private final TrainingPlanRepository trainingPlanRepository;
    private final RunnerRepository runnerRepository;
    private final TrainingSessionRepository trainingSessionRepository;
    private final MaterialRepository materialRepository;
    private final TrainingProgressRepository trainingProgressRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public TrainingSessionResultService(TrainingSessionResultRepository trainingSessionResultRepository, TrainingPlanRepository trainingPlanRepository, RunnerRepository runnerRepository, TrainingSessionRepository trainingSessionRepository, MaterialRepository materialRepository, TrainingProgressRepository trainingProgressRepository, ActivityRepository activityRepository) {
        this.trainingSessionResultRepository = trainingSessionResultRepository;
        this.trainingPlanRepository = trainingPlanRepository;
        this.runnerRepository = runnerRepository;
        this.trainingSessionRepository = trainingSessionRepository;
        this.materialRepository = materialRepository;
        this.trainingProgressRepository = trainingProgressRepository;
        this.activityRepository = activityRepository;
    }



    public ResponseEntity<Object> getHasResultsUserInAPlan(Long userId, Long planId) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<TrainingPlan> query = this.trainingPlanRepository.findById(planId);
        if (!query.isPresent()) {
            data.put("error", "Plan not exists");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        TrainingPlan tp = query.get();
        List<TrainingWeek> weeks = tp.getTrainingWeeks();
        List<List<Boolean>> info = new ArrayList<>();
        List<List<TrainingSession>> sessions = new ArrayList<>();
        weeks.stream().forEach(week -> {
            List<TrainingSession> weekSessions = week.getSessions();
            Collections.sort(weekSessions, Comparator.comparingInt(TrainingSession::getDay));
            sessions.add(weekSessions);
        });

        for (int k = 0; k < tp.getWeeks(); ++k) {
            List<Boolean> temp = new ArrayList<>();
            for (int l = 0; l < 7; ++l) {
                TrainingSession trainingSession = sessions.get(k).get(l);
                Optional<TrainingSessionResult> queryResult = this.trainingSessionResultRepository.existsResultForUserAndSession(trainingSession.getId(), userId);
                if (queryResult.isPresent()) {
                    temp.add(true);
                }
                else {
                    temp.add(false);
                }
            }
            info.add(temp);
        }
        data.put("data", info);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> createRunningSessionResult(CreateRunningSessionResultDTO createRunningSessionResultDTO) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<TrainingPlan> query = this.trainingPlanRepository.findById(createRunningSessionResultDTO.getPlanId());
        if (!query.isPresent()) {
            data.put("error", "The plan does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Optional<Runner> query2 = this.runnerRepository.findById(createRunningSessionResultDTO.getUserId());
        if (!query2.isPresent()) {
            data.put("error", "The user does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Optional<TrainingSession> query3 = this.trainingSessionRepository.findById(createRunningSessionResultDTO.getSessionId());
        if (!query3.isPresent()) {
            data.put("error", "The session does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        TrainingPlan trainingPlan = query.get();
        Runner runner = query2.get();
        TrainingSession trainingSession = query3.get();

        String name = trainingSession.getName();
        String description = createRunningSessionResultDTO.getDescription();
        LocalDateTime date = createRunningSessionResultDTO.getDate();
        Integer effort = createRunningSessionResultDTO.getEffort();
        Float distance = createRunningSessionResultDTO.getDistance();
        LocalTime duration = createRunningSessionResultDTO.getDuration();
        Float pace = createRunningSessionResultDTO.getPace();
        Integer fcAvg = createRunningSessionResultDTO.getFcAvg();

        List<Long> materialsId = createRunningSessionResultDTO.getMaterialsId();
        Set<Material> materials = new HashSet<>();
        materialsId.stream().forEach(materialId -> {
            Optional<Material> query_mat = this.materialRepository.findById(materialId);
            if (query_mat.isPresent()) {
                Material mat = query_mat.get();
                mat.addMileage(distance);
                this.materialRepository.save(mat);
                materials.add(mat);
            }
        });

        RunningSessionResult runningSessionResult = new RunningSessionResult();
        runningSessionResult.setName(name);
        runningSessionResult.setDescription(description);
        runningSessionResult.setDate(date);
        runningSessionResult.setEffort(effort);
        runningSessionResult.setDistance(distance);
        runningSessionResult.setDuration(duration);
        runningSessionResult.setPace(pace);
        runningSessionResult.setFcAvg(fcAvg);
        runningSessionResult.setSession(trainingSession);
        runningSessionResult.setMaterials(materials);
        runningSessionResult.setRunner(runner);
        TrainingSessionResult savedResult = this.trainingSessionResultRepository.save(runningSessionResult);
        trainingSession.addResult(savedResult);

        Integer totalSessions = 0;
        List<TrainingWeek> weeks = trainingPlan.getTrainingWeeks();
        for (TrainingWeek week : weeks) {
            totalSessions += week.getNumSessions();
        }

        List<TrainingSessionResult> userResultsInAPlan = this.trainingSessionResultRepository.findAllByPlanAndRunner(trainingPlan.getId(), createRunningSessionResultDTO.getUserId());
        Float percentage = (float) userResultsInAPlan.size() / totalSessions;
        Optional<TrainingProgress> tp = this.trainingProgressRepository.findProgressByPlanAndRunner(createRunningSessionResultDTO.getUserId(),trainingPlan.getId());
        TrainingProgress trainingProgress = tp.get();
        trainingProgress.setPercentage(percentage * 100);
        this.trainingProgressRepository.save(trainingProgress);
        data.put("data", savedResult.getId());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> addRouteToRunningSessionResult(Long trainingSessionResultId, MultipartFile route) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<TrainingSessionResult> query = this.trainingSessionResultRepository.findById(trainingSessionResultId);
        if (!query.isPresent()) {
            data.put("error", "Training session result does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        RunningSessionResult rsr = (RunningSessionResult) query.get();
        try {
            byte[] routeBytes = route.getBytes();
            rsr.setRoute(routeBytes);
            this.trainingSessionResultRepository.save(rsr);
            data.put("data", "Route set properly");
            return new ResponseEntity<>(data,HttpStatus.OK);
        }
        catch(IOException e) {
            data.put("error", "Error uploading the route");
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> createStrengthSessionResult(CreateStrengthSessionResultDTO createStrengthSessionResult) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<TrainingPlan> query = this.trainingPlanRepository.findById(createStrengthSessionResult.getPlanId());
        if (!query.isPresent()) {
            data.put("error", "The plan does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Optional<Runner> query2 = this.runnerRepository.findById(createStrengthSessionResult.getUserId());
        if (!query2.isPresent()) {
            data.put("error", "The user does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Optional<TrainingSession> query3 = this.trainingSessionRepository.findById(createStrengthSessionResult.getSessionId());
        if (!query3.isPresent()) {
            data.put("error", "The session does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        TrainingPlan trainingPlan = query.get();
        Runner runner = query2.get();
        TrainingSession trainingSession = query3.get();

        String name = trainingSession.getName();
        String description = createStrengthSessionResult.getDescription();
        LocalDateTime date = createStrengthSessionResult.getDate();
        Integer effort = createStrengthSessionResult.getEffort();


        List<Long> materialsId = createStrengthSessionResult.getMaterialsId();
        Set<Material> materials = new HashSet<>();
        materialsId.stream().forEach(materialId -> {
            Optional<Material> query_mat = this.materialRepository.findById(materialId);
            if (query_mat.isPresent()) {
                Material mat = query_mat.get();
                materials.add(mat);
            }
        });
        StrengthSessionResult ssr = new StrengthSessionResult();
        ssr.setName(name);
        ssr.setDescription(description);
        ssr.setDate(date);
        ssr.setEffort(effort);
        ssr.setSession(trainingSession);
        ssr.setMaterials(materials);
        ssr.setRunner(runner);
        TrainingSessionResult savedResult = this.trainingSessionResultRepository.save(ssr);
        trainingSession.addResult(savedResult);

        Integer totalSessions = 0;
        List<TrainingWeek> weeks = trainingPlan.getTrainingWeeks();
        for (TrainingWeek week : weeks) {
            totalSessions += week.getNumSessions();
        }

        List<TrainingSessionResult> userResultsInAPlan = this.trainingSessionResultRepository.findAllByPlanAndRunner(trainingPlan.getId(), createStrengthSessionResult.getUserId());
        Float percentage = (float) userResultsInAPlan.size() / totalSessions;
        Optional<TrainingProgress> tp = this.trainingProgressRepository.findProgressByPlanAndRunner(createStrengthSessionResult.getUserId(),trainingPlan.getId());
        TrainingProgress trainingProgress = tp.get();
        trainingProgress.setPercentage(percentage * 100);
        this.trainingProgressRepository.save(trainingProgress);
        data.put("data", savedResult.getId());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> createMobilitySessionResult(CreateMobilitySessionResultDTO createMobilitySessionResultDTO) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<TrainingPlan> query = this.trainingPlanRepository.findById(createMobilitySessionResultDTO.getPlanId());
        if (!query.isPresent()) {
            data.put("error", "The plan does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Optional<Runner> query2 = this.runnerRepository.findById(createMobilitySessionResultDTO.getUserId());
        if (!query2.isPresent()) {
            data.put("error", "The user does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Optional<TrainingSession> query3 = this.trainingSessionRepository.findById(createMobilitySessionResultDTO.getSessionId());
        if (!query3.isPresent()) {
            data.put("error", "The session does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        TrainingPlan trainingPlan = query.get();
        Runner runner = query2.get();
        TrainingSession trainingSession = query3.get();

        String name = trainingSession.getName();
        String description = createMobilitySessionResultDTO.getDescription();
        LocalDateTime date = createMobilitySessionResultDTO.getDate();
        Integer effort = createMobilitySessionResultDTO.getEffort();


        List<Long> materialsId = createMobilitySessionResultDTO.getMaterialsId();
        Set<Material> materials = new HashSet<>();
        materialsId.stream().forEach(materialId -> {
            Optional<Material> query_mat = this.materialRepository.findById(materialId);
            if (query_mat.isPresent()) {
                Material mat = query_mat.get();
                materials.add(mat);
            }
        });
        MobilitySessionResult msr = new MobilitySessionResult();
        msr.setName(name);
        msr.setDescription(description);
        msr.setDate(date);
        msr.setEffort(effort);
        msr.setSession(trainingSession);
        msr.setMaterials(materials);
        msr.setRunner(runner);
        TrainingSessionResult savedResult = this.trainingSessionResultRepository.save(msr);
        trainingSession.addResult(savedResult);

        Integer totalSessions = 0;
        List<TrainingWeek> weeks = trainingPlan.getTrainingWeeks();
        for (TrainingWeek week : weeks) {
            totalSessions += week.getNumSessions();
        }

        List<TrainingSessionResult> userResultsInAPlan = this.trainingSessionResultRepository.findAllByPlanAndRunner(trainingPlan.getId(), createMobilitySessionResultDTO.getUserId());
        Float percentage = (float) userResultsInAPlan.size() / totalSessions;
        Optional<TrainingProgress> tp = this.trainingProgressRepository.findProgressByPlanAndRunner(createMobilitySessionResultDTO.getUserId(),trainingPlan.getId());
        TrainingProgress trainingProgress = tp.get();
        trainingProgress.setPercentage(percentage * 100);
        this.trainingProgressRepository.save(trainingProgress);
        data.put("data", savedResult.getId());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> getTrainingResult(Long sessionId, String type) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<TrainingSessionResult> query = this.trainingSessionResultRepository.findById(sessionId);
        if (!query.isPresent()) {
            data.put("error", "Session does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        if (type.equals("RunningResult")) {
            RunningSessionResult runningSessionResult = (RunningSessionResult) query.get();
            String planName = runningSessionResult.getSession().getTrainingWeek().getTrainingPlan().getName();
            RunningResultDTO rDTO = new RunningResultDTO(runningSessionResult, planName);
            data.put("data", rDTO);
        }
        else if (type.equals("StrengthResult")) {
            StrengthSessionResult ssr = (StrengthSessionResult) query.get();
            String planName = ssr.getSession().getTrainingWeek().getTrainingPlan().getName();
            StrengthResultDTO sDTO = new StrengthResultDTO(ssr, planName);
            data.put("data", sDTO);
        }
        else {
            MobilitySessionResult msr = (MobilitySessionResult) query.get();
            String planName = msr.getSession().getTrainingWeek().getTrainingPlan().getName();
            MobilityResultDTO sDTO = new MobilityResultDTO(msr, planName);
            data.put("data", sDTO);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteSessionResult(Long sessionId) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<TrainingSessionResult> query = this.trainingSessionResultRepository.findById(sessionId);
        if (!query.isPresent()) {
            data.put("error", "Training Session Result does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        TrainingSessionResult tsr = query.get();
        TrainingSession ts = tsr.getSession();
        ts.removeResult(tsr);
        this.trainingSessionRepository.save(ts);
        this.activityRepository.delete(tsr);

        Integer totalSessions = 0;
        List<TrainingWeek> weeks = tsr.getSession().getTrainingWeek().getTrainingPlan().getTrainingWeeks();
        for (TrainingWeek week : weeks) {
            totalSessions += week.getNumSessions();
        }
        List<TrainingSessionResult> userResultsInAPlan = this.trainingSessionResultRepository.findAllByPlanAndRunner(tsr.getSession().getTrainingWeek().getTrainingPlan().getId(), tsr.getRunner().getId());
        Float percentage = (float) userResultsInAPlan.size() / totalSessions;
        Optional<TrainingProgress> tp = this.trainingProgressRepository.findProgressByPlanAndRunner(tsr.getRunner().getId(),tsr.getSession().getTrainingWeek().getTrainingPlan().getId());
        TrainingProgress trainingProgress = tp.get();
        trainingProgress.setPercentage(percentage * 100);
        this.trainingProgressRepository.save(trainingProgress);

        data.put("data", "Session result deleted successfully");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateResult(Long trainingSessionId, UpdateResultDTO updateResultDTO) {
        System.out.println(updateResultDTO);
        HashMap<String, Object> data = new HashMap<>();
        Optional<TrainingSessionResult> query = this.trainingSessionResultRepository.findById(trainingSessionId);
        if (!query.isPresent()) {
            data.put("error", "The training session result does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        String type = updateResultDTO.getType();
        TrainingSessionResult tsr = query.get();
        tsr.setDescription(updateResultDTO.getDescription());
        tsr.setEffort(updateResultDTO.getEffort());
        tsr.setDate(updateResultDTO.getDate());

        List<Long> materialsId = updateResultDTO.getMaterialsId();
        Set<Material> materials = new HashSet<>();
        materialsId.stream().forEach(materialId -> {
            Optional<Material> query_mat = this.materialRepository.findById(materialId);
            if (query_mat.isPresent()) {
                Material mat = query_mat.get();
                if (type.equals("RunningResult")) {
                    mat.addMileage(updateResultDTO.getDistance());
                    Material savedMat = this.materialRepository.save(mat);
                    materials.add(savedMat);
                }
                else materials.add(mat);
            }
        });


        if (type.equals("RunningResult")) {
            RunningSessionResult rsr = (RunningSessionResult) tsr;
            rsr.setDistance(updateResultDTO.getDistance());
            rsr.setDuration(updateResultDTO.getDuration());
            rsr.setPace(updateResultDTO.getPace());
            rsr.setFcAvg(updateResultDTO.getFcAvg());

        }
        this.trainingSessionResultRepository.save(tsr);
        data.put("data", tsr.getId());
        return new ResponseEntity<>(data, HttpStatus.OK);

    }
}
