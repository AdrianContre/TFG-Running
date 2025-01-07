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
    private final TrainingSessionResultRepository trainingSessionResultRepository;
    private final ActivityRepository activityRepository;
    private final TrainingGroupRepository trainingGroupRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public TrainingPlanService(TrainingPlanRepository trainingPlanRepository, TrainingWeekRepository trainingWeekRepository, TrainingSessionRepository trainingSessionRepository, TrainerRepository trainerRepository, RunnerRepository runnerRepository, TrainingProgressRepository trainingProgressRepository, RestSessionRepository restSessionRepository, TrainingSessionResultRepository trainingSessionResultRepository, ActivityRepository activityRepository, TrainingGroupRepository trainingGroupRepository, CommentRepository commentRepository) {
        this.trainingPlanRepository = trainingPlanRepository;
        this.trainingWeekRepository = trainingWeekRepository;
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainerRepository = trainerRepository;
        this.runnerRepository = runnerRepository;
        this.trainingProgressRepository = trainingProgressRepository;
        this.trainingSessionResultRepository = trainingSessionResultRepository;
        this.activityRepository = activityRepository;
        this.trainingGroupRepository = trainingGroupRepository;
        this.commentRepository = commentRepository;
    }

    public ResponseEntity<Object> createTrainingPlan(CreateTrainingPlanDTO trainingPlanDTO) {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User user = u.getUser();
        Long trainerId = trainingPlanDTO.getTrainerId();
        Optional<Trainer> query = this.trainerRepository.findById(trainerId);
        if (!query.isPresent()) {
            data.put("error", "Trainer not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(user.getId(), trainerId)) {
            data.put("error", "You cannot create training plans on behalf of other users");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        Trainer trainer = query.get();
        String name = trainingPlanDTO.getName();
        String description = trainingPlanDTO.getDescription();
        Integer weeks = trainingPlanDTO.getNumWeeks();
        String objDistance = trainingPlanDTO.getObjDistance();
        String level = trainingPlanDTO.getLevel();
        String wearMaterial = trainingPlanDTO.getWearMaterial();
        List<TrainingWeek> trainingWeekList = new ArrayList<>();
        List<TrainingProgress> trainingProgressesList = new ArrayList<>();
        TrainingPlan trainingPlan = new TrainingPlan(name, description, weeks, objDistance, level, trainer, trainingWeekList, trainingProgressesList);
        trainingPlan.setWearMaterial(wearMaterial);
        Set<TrainingGroup> groups = new HashSet<>();
        for (Long groupId : trainingPlanDTO.getGroupsId()) {
            Optional<TrainingGroup> query_group =  this.trainingGroupRepository.findById(groupId);
            if (!query_group.isPresent()) {
                data.put("error", "Group not found");
                return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
            }
            TrainingGroup tg = query_group.get();
            tg.addTrainingPlan(trainingPlan);
            groups.add(tg);
        }
        trainingPlan.setGroups(groups);
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
                    Integer day = j;

                    TrainingSession savedSession;
                    if (type.equals("running")) {
                        RunningSession runningSession = new RunningSession(sessionDTO.getRunningType(), sessionDTO.getDistance(), sessionDTO.getDuration());
                        runningSession.setName(sessionName);
                        runningSession.setDescription(sessionDescription);
                        runningSession.setTrainingWeek(savedWeek);
                        runningSession.setResults(new ArrayList<>());
                        runningSession.setDay(day);
                        savedSession = this.trainingSessionRepository.save(runningSession);
                    } else if (type.equals("strength")) {
                        StrengthSession strengthSession = new StrengthSession();
                        strengthSession.setName(sessionName);
                        strengthSession.setDescription(sessionDescription);
                        strengthSession.setTrainingWeek(savedWeek);
                        strengthSession.setResults(new ArrayList<>());
                        strengthSession.setDay(day);
                        savedSession = this.trainingSessionRepository.save(strengthSession);
                    } else if (type.equals("mobility")) {
                        MobilitySession mobilitySession = new MobilitySession();
                        mobilitySession.setName(sessionName);
                        mobilitySession.setDescription(sessionDescription);
                        mobilitySession.setTrainingWeek(savedWeek);
                        mobilitySession.setResults(new ArrayList<>());
                        mobilitySession.setDay(day);
                        savedSession = this.trainingSessionRepository.save(mobilitySession);
                    }
                    else {
                        RestSession restSession = new RestSession();
                        restSession.setName(sessionName);
                        restSession.setDescription(sessionDescription);
                        restSession.setTrainingWeek(savedWeek);
                        restSession.setResults(new ArrayList<>());
                        restSession.setDay(day);
                        savedSession = this.trainingSessionRepository.save(restSession);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User user = u.getUser();
        Optional<Trainer> query = this.trainerRepository.findById(trainerId);
        if (!query.isPresent()) {
            data.put("error", "Trainer not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        if (!trainerId.equals(user.getId())) {
            data.put("error", "You can not get other trainer plans");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
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
        List<GroupDTO> groupsDTO = new ArrayList<>();
        Set<TrainingGroup> groups = plan.getGroups();
        for (TrainingGroup group : groups) {
            GroupDTO dto = new GroupDTO(group.getId(), group.getName(), group.getTrainer().getName());
            groupsDTO.add(dto);
        }
        info.setGroups(groupsDTO);
        data.put("data", info);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> enrollUserToAPlan(Long planId, EnrrollToAPlanDTO body) {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User user = u.getUser();
        Long userId = user.getId();
        Optional<Runner> query = this.runnerRepository.findById(body.getUserId());
        if (!query.isPresent()) {
            data.put("error", "User not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(userId, body.getUserId())) {
            data.put("error", "You can not enroll other users to a plan");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User user = u.getUser();
        Long userId = user.getId();
        Optional<Runner> query = this.runnerRepository.findById(runnerId);
        if (!query.isPresent()) {
            data.put("error", "User not found");
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        if (!Objects.equals(userId, runnerId)) {
            data.put("error", "You can not get other users enrolled plans");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        List<TrainingProgress> tps = this.trainingProgressRepository.findAllUserProgress(runnerId);
        if (tps.isEmpty()) {
            data.put("data", new ArrayList<>());
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        List<EnrolledPlanDTO> info = new ArrayList<>();
        tps.stream().forEach(tp -> {
            TrainingPlan trainingPlan = tp.getTrainingPlan();
            List<TrainingSessionResult> userResultsInAPlan = this.trainingSessionResultRepository.findAllByPlanAndRunner(trainingPlan.getId(), runnerId);
            EnrolledPlanDTO planDTO = new EnrolledPlanDTO(trainingPlan, tp.getPercentage());
            planDTO.setSessionsCompleted(userResultsInAPlan.size());
            info.add(planDTO);
        });
        data.put("data", info);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> withdrawUserInPlan(Long planId, EnrrollToAPlanDTO body) {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User user = u.getUser();
        Long userId = body.getUserId();
        Optional<Runner> query = this.runnerRepository.findById(userId);
        if (!query.isPresent()) {
            data.put("error", "Runner not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        if (!userId.equals(user.getId())) {
            data.put("error", "You can not withdraw other users to a plan");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }

        List<TrainingSessionResult> trainingSessionResults = this.trainingSessionResultRepository.findAllByPlanAndRunner(planId, userId);
        for (TrainingSessionResult tsr : trainingSessionResults) {
            TrainingSession ts = tsr.getSession();
            ts.removeResult(tsr);
            this.trainingSessionRepository.save(ts);
            this.activityRepository.delete(tsr);
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

    public ResponseEntity<Object> updateTrainingPlan(Long planId, UpdateTrainingPlanDTO updateTrainingPlanDTO) {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation udp = (UserDetailsImplementation) authentication.getPrincipal();
        User user = udp.getUser();
        Long userId = user.getId();
        Optional<TrainingPlan> query = this.trainingPlanRepository.findById(planId);
        if (!query.isPresent()) {
            data.put("error", "The plan does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(userId, query.get().getCreator().getId())) {
            data.put("error", "You can only edit a plan you have created");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }

        TrainingPlan trainingPlan = query.get();
        trainingPlan.setName(updateTrainingPlanDTO.getName());
        trainingPlan.setDescription(updateTrainingPlanDTO.getDescription());
        trainingPlan.setWeeks(updateTrainingPlanDTO.getNumWeeks());
        trainingPlan.setLevel(updateTrainingPlanDTO.getLevel());
        trainingPlan.setDistanceObjective(updateTrainingPlanDTO.getObjDistance());
        trainingPlan.setWearMaterial(updateTrainingPlanDTO.getWearMaterial());

        List<TrainingWeek> trainingWeeks = trainingPlan.getTrainingWeeks();
        List<List<SessionDTO>> sessionsDTO = updateTrainingPlanDTO.getSessions();
        Integer numWeeks = updateTrainingPlanDTO.getNumWeeks();

        if (numWeeks > trainingWeeks.size()) {
            for (int i = trainingWeeks.size(); i < numWeeks; ++i) {
                trainingWeeks.add(new TrainingWeek(trainingPlan, new ArrayList<>(), new ArrayList<>()));
            }
        } else if (numWeeks < trainingWeeks.size()) {
            List<TrainingWeek> remainingWeeks = new ArrayList<>(trainingWeeks.subList(0, numWeeks));
            trainingPlan.getTrainingWeeks().clear();
            trainingPlan.getTrainingWeeks().addAll(remainingWeeks);
            for (int i = numWeeks; i < trainingWeeks.size(); i++) {
                TrainingWeek week = trainingWeeks.get(i);
                for (TrainingSession sess : week.getSessions()) {
                    sess.getResults().clear();
                    sess.setTrainingWeek(null);
                    this.trainingSessionRepository.delete(sess);
                }
                for (Comment comment : week.getComments()) {
                    comment.setTrainingWeek(null);
                    User u = comment.getSender();
                    u.removeComment(comment);
                    this.commentRepository.delete(comment);
                }
                week.setTrainingPlan(null);
                this.trainingWeekRepository.delete(week);
            }
        }

        for (int i = 0; i < numWeeks; ++i) {
            TrainingWeek currentWeek = trainingWeeks.get(i);
            List<SessionDTO> sessionsInWeekDTO = sessionsDTO.get(i);
            List<TrainingSession> sessions = currentWeek.getSessions();
            List<TrainingSession> tempSessions = new ArrayList<>(7);

            if (sessions.isEmpty()) {
                for (int j = 0; j < 7; ++j) {
                    SessionDTO sessionDTO = sessionsInWeekDTO.get(j);
                    TrainingSession newSession = createNewSession(sessionDTO, currentWeek);
                    newSession.setDay(j);
                    tempSessions.add(j, newSession);
                }
            }
            else {
                for (int j = 0; j < 7; ++j) {
                    SessionDTO sessionDTO = sessionsInWeekDTO.get(j);
                    TrainingSession session = sessions.get(j);

                    if (!isMatchingSessionType(session, sessionDTO.getType())) {
                        session.getResults().clear();
                        session.setTrainingWeek(null);
                        this.trainingSessionRepository.delete(session);
                        TrainingSession newSession = createNewSession(sessionDTO, currentWeek);
                        newSession.setDay(j);
                        tempSessions.add(newSession);
                    } else if (session != null) {
                        updateExistingSession(session, sessionDTO);
                        session.setDay(j);
                        tempSessions.add(j, session);
                    } else {
                        TrainingSession newSession = createNewSession(sessionDTO, currentWeek);
                        newSession.setDay(j);
                        tempSessions.add(j, newSession);
                    }
                }
            }

            currentWeek.getSessions().clear();
            currentWeek.getSessions().addAll(tempSessions);
            trainingWeeks.set(i, this.trainingWeekRepository.save(currentWeek));
        }

        List<Long> newGroupsId = updateTrainingPlanDTO.getGroupsId();
        List<TrainingGroup> newGroups = this.trainingGroupRepository.findAllById(newGroupsId);
        Set<TrainingGroup> newGroupsSet = new HashSet<>();
        for (TrainingGroup tg : newGroups) {
            newGroupsSet.add(tg);
            tg.addTrainingPlan(trainingPlan);
        }
        Set<TrainingGroup> currentGroups = trainingPlan.getGroups();
        Set<TrainingGroup> removedGroups = new HashSet<>(currentGroups);
        removedGroups.removeAll(newGroupsSet);
        trainingPlan.setGroups(newGroupsSet);

        Set<Runner> runnersOutOfPlan = new HashSet<>();
        for (TrainingGroup trainingGroup : removedGroups) {
            trainingGroup.removeTrainingPlan(trainingPlan);
            for (Runner runner : trainingGroup.getRunners()) {
                runnersOutOfPlan.add(runner);
            }
        }
        for (Runner r : runnersOutOfPlan) {
            List<TrainingSessionResult> trainingSessionResults = this.trainingSessionResultRepository.findAllByPlanAndRunner(planId, r.getId());
            for (TrainingSessionResult tsr : trainingSessionResults) {
                TrainingSession ts = tsr.getSession();
                ts.removeResult(tsr);
                this.trainingSessionRepository.save(ts);
                this.activityRepository.delete(tsr);
            }

            Optional<TrainingProgress> query2 = this.trainingProgressRepository.findProgressByPlanAndRunner(r.getId(), planId);
            if (query2.isPresent()) {
                TrainingProgress tp = query2.get();
                this.trainingProgressRepository.delete(tp);
            }

        }

        this.trainingPlanRepository.save(trainingPlan);

        data.put("data", "Training plan updated properly");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }



    private TrainingSession createNewSession(SessionDTO sessionDTO, TrainingWeek trainingWeek) {
        TrainingSession newSession;
        String type = sessionDTO.getType();

        if (type.equals("running")) {
            newSession = new RunningSession(sessionDTO.getRunningType(), sessionDTO.getDistance(), sessionDTO.getDuration());
        } else if (type.equals("strength")) {
            newSession = new StrengthSession();
        } else if (type.equals("mobility")) {
            newSession = new MobilitySession();
        }
        else {
            newSession = new RestSession();
        }

        newSession.setName(sessionDTO.getName());
        newSession.setDescription(sessionDTO.getDescription());
        newSession.setResults(new ArrayList<>());
        newSession.setTrainingWeek(trainingWeek);
        return newSession;
    }


    private boolean isMatchingSessionType(TrainingSession session, String type) {
        return (type.equals("running") && session instanceof RunningSession) ||
                (type.equals("strength") && session instanceof StrengthSession) ||
                (type.equals("mobility") && session instanceof MobilitySession) ||
                (type.equals("rest") && session instanceof RestSession);
    }

    private void updateExistingSession(TrainingSession session, SessionDTO sessionDTO) {
        session.setName(sessionDTO.getName());
        session.setDescription(sessionDTO.getDescription());

        if (session instanceof RunningSession) {
            RunningSession runningSession = (RunningSession) session;
            runningSession.setType(sessionDTO.getRunningType());
            runningSession.setDistance(sessionDTO.getDistance());
            runningSession.setDuration(sessionDTO.getDuration());
        }
    }

    public ResponseEntity<Object> getElegibleTrainingPlans() {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User user = u.getUser();
        Long userId = user.getId();

        List<TrainingPlan> plans = this.trainingPlanRepository.findEligibleTrainingPlans(userId);
        List<ListPlansDTO> plansDTO = new ArrayList<>();
        plans.stream().forEach(plan -> {
            ListPlansDTO p = new ListPlansDTO(plan);
            plansDTO.add(p);
        });
        data.put("data", plansDTO);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
}
