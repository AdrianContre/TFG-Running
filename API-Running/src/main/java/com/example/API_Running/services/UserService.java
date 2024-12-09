package com.example.API_Running.services;

import com.example.API_Running.dtos.RunnerDTO;
import com.example.API_Running.dtos.SimpleUserDTO;
import com.example.API_Running.dtos.TrainerDTO;
import com.example.API_Running.models.*;
import com.example.API_Running.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RunnerRepository runnerRepository;
    private final TrainerRepository trainerRepository;
    private final CommentRepository commentRepository;
    private final TrainingGroupRepository trainingGroupRepository;
    private final ManualActivityRepository manualActivityRepository;
    private final MaterialRepository materialRepository;
    private final ActivityRepository activityRepository;
    private final TrainingProgressRepository trainingProgressRepository;
    private final TrainingSessionRepository trainingSessionRepository;
    private final TrainingSessionResultRepository trainingSessionResultRepository;
    private final TrainingPlanRepository trainingPlanRepository;
    private final TrainingWeekRepository trainingWeekRepository;

    @Autowired
    public UserService(UserRepository userRepository, RunnerRepository runnerRepository, TrainerRepository trainerRepository, CommentRepository commentRepository, TrainingGroupRepository trainingGroupRepository, ManualActivityRepository manualActivityRepository, MaterialRepository materialRepository, ActivityRepository activityRepository, TrainingProgressRepository trainingProgressRepository, TrainingSessionRepository trainingSessionRepository, TrainingSessionResultRepository trainingSessionResultRepository, TrainingPlanRepository trainingPlanRepository, TrainingWeekRepository trainingWeekRepository) {
        this.userRepository = userRepository;
        this.runnerRepository = runnerRepository;
        this.trainerRepository = trainerRepository;
        this.commentRepository = commentRepository;
        this.trainingGroupRepository = trainingGroupRepository;
        this.manualActivityRepository = manualActivityRepository;
        this.materialRepository = materialRepository;
        this.activityRepository = activityRepository;
        this.trainingProgressRepository = trainingProgressRepository;
        this.trainingSessionRepository = trainingSessionRepository;
        this.trainingSessionResultRepository = trainingSessionResultRepository;
        this.trainingPlanRepository = trainingPlanRepository;
        this.trainingWeekRepository = trainingWeekRepository;
    }



    public ResponseEntity<Object> whoAmI() {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User user = u.getUser();
        if (user instanceof Trainer) {
            Optional<Trainer> query = this.trainerRepository.findByUsername(user.getUsername());
            Trainer trainer = query.get();
            TrainerDTO t = new TrainerDTO(trainer);
            data.put("userLogged",t);
        }
        else {
            Optional<Runner> query = this.runnerRepository.findByUsername(u.getUsername());
            Runner runner = query.get();
            RunnerDTO r = new RunnerDTO(runner);
            data.put("userLogged", r);
        }
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> uploadProfilePicture(Long userId, MultipartFile profilePicture) {
        HashMap<String,Object> data = new HashMap<>();
        Optional<User> query = this.userRepository.findById(userId);
        if (!query.isPresent()) {
            data.put("error", "User not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        User u = query.get();
        try {
            byte[] profileBytes = profilePicture.getBytes();
            u.setProfilePicture(profileBytes);
            this.userRepository.save(u);
            data.put("data", "Profile picture set properly");
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (IOException e) {
            data.put("error", "Error uploading profile picture");
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllUsers() {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        List<SimpleUserDTO> usersDTOS = new ArrayList<>();
        List<User> users = this.userRepository.findAll();
        users.forEach(user -> {
            if (userAuth.getId() != user.getId()) {
                SimpleUserDTO userDTO = new SimpleUserDTO(user.getId(), user.getUsername());
                usersDTOS.add(userDTO);
            }
        });
        data.put("data",usersDTOS);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


    public ResponseEntity<Object> deleteUser(Long userId) {
        HashMap<String, Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();

        if (!Objects.equals(u.getUser().getId(), userId)) {
            data.put("error", "You can only delete your own user");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        
        List<Comment> user_comments = new ArrayList<>(user.getComments());
        for (Comment c : user_comments) {
            TrainingWeek tw = c.getTrainingWeek();
            tw.removeComment(c);
            c.setTrainingWeek(null);
            user.removeComment(c);
            this.commentRepository.delete(c);
        }


        Runner r = (Runner) user;

        Set<TrainingGroup> tgs = r.getGroups();
        for (TrainingGroup tg : tgs) {
            tg.removeUser(r);
            r.removeGroup(tg);
            this.trainingGroupRepository.save(tg);
        }


        List<ManualActivity> manualActivities = this.manualActivityRepository.findByRunner(r);
        for (ManualActivity ma : manualActivities) {
            this.manualActivityRepository.delete(ma);
        }


        List<TrainingSessionResult> trainingSessionResults = this.trainingSessionResultRepository.findAllByRunner(userId);
        for (TrainingSessionResult tsr : trainingSessionResults) {
            TrainingSession ts = tsr.getSession();
            ts.removeResult(tsr);
            this.trainingSessionRepository.save(ts);
            this.activityRepository.delete(tsr);
        }

        List<TrainingProgress> progresses = this.trainingProgressRepository.findAllUserProgress(userId);

        for (TrainingProgress tp : progresses) {
            this.trainingProgressRepository.delete(tp);
        }


        this.materialRepository.deleteAll(r.getMaterials());


        Optional<Trainer> trainer_query = this.trainerRepository.findById(userId);
        if (trainer_query.isPresent()) {
            Trainer t = trainer_query.get();


            List<TrainingGroup> groups = this.trainingGroupRepository.findAllByCreator(t.getId());
            for (TrainingGroup tg : groups) {
                Set<Runner> group_runners = new HashSet<>(tg.getRunners());;
                for (Runner runner : group_runners) {
                    runner.removeGroup(tg);
                    tg.removeUser(runner);
                }
                Set<TrainingPlan> plans = tg.getTrainingPlans();
                for (TrainingPlan plan : plans) {
                    plan.removeGroup(tg);
                    tg.removeTrainingPlan(plan);
                }
                tg.setTrainer(null);
                t.removeManagedGroups(tg);
                this.trainingGroupRepository.delete(tg);
            }


            List<TrainingPlan> trainingPlans = this.trainingPlanRepository.findAllByTrainerId(t.getId());
            for (TrainingPlan p : trainingPlans) {


                List<TrainingProgress> ps = this.trainingProgressRepository.findProgressesByPlanId(p.getId());
                for (TrainingProgress tpg : ps) {
                    tpg.getRunner().removeTrainingProgress(tpg);
                    tpg.setTrainingPlan(null);
                    p.removeProgress(tpg);
                    this.trainingProgressRepository.delete(tpg);
                }


                List<TrainingWeek> weeks = new ArrayList<>(p.getTrainingWeeks());
                for (TrainingWeek week : weeks) {


                    List<Comment> comments = new ArrayList<>(week.getComments());
                    for (Comment c : comments) {
                        week.removeComment(c);
                        c.setTrainingWeek(null);
                        c.getSender().removeComment(c);
                        this.commentRepository.delete(c);
                    }


                    List<TrainingSession> sessions = new ArrayList<>(week.getSessions());
                    for (TrainingSession session : sessions) {
                        List<TrainingSessionResult> results = new ArrayList<>(session.getResults());
                        for (TrainingSessionResult result : results) {
                            session.removeResult(result);
                            this.activityRepository.delete(result);
                        }
                        session.setTrainingWeek(null);
                        week.removeSession(session);
                        this.trainingSessionRepository.delete(session);
                    }
                    week.setTrainingPlan(null);
                    p.removeWeek(week);
                    this.trainingWeekRepository.delete(week);
                }
                p.setCreator(null);
                t.removeTrainingPlan(p);
                this.trainingPlanRepository.delete(p);
            }

        }


        this.userRepository.delete(user);
        data.put("data", "User deleted successfully");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
