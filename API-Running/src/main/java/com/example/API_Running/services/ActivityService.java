package com.example.API_Running.services;

import com.example.API_Running.dtos.ActivityDTO;
import com.example.API_Running.models.*;
import com.example.API_Running.repository.ActivityRepository;
import com.example.API_Running.repository.ManualActivityRepository;
import com.example.API_Running.repository.RunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final RunnerRepository runnerRepository;
    private final ManualActivityRepository manualActivityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, RunnerRepository runnerRepository,ManualActivityRepository manualActivityRepository) {
        this.activityRepository = activityRepository;
        this.runnerRepository = runnerRepository;
        this.manualActivityRepository = manualActivityRepository;
    }


    public ResponseEntity<Object> getUserActivities(Long runnerId) {
        HashMap<String,Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), runnerId)) {
            data.put("error", "You can not get other runner's activities");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        Optional<Runner> query = this.runnerRepository.findById(runnerId);
        if (!query.isPresent()) {
            data.put("error", "Runner not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        List<Activity> activities = this.activityRepository.findActivitiesByRunnerId(runnerId);
        List<Activity> sortedAct = activities.stream().sorted((a1, a2) -> a2.getDate().compareTo(a1.getDate())).collect(Collectors.toList());
        List<ActivityDTO> activitiesDTO =  new ArrayList<>();
        sortedAct.stream().forEach(activity -> {
            if (activity instanceof ManualActivity) {
                Optional<ManualActivity> manAct = this.manualActivityRepository.findById(activity.getId());
                ManualActivity mAct = manAct.get();
                ActivityDTO actDTO = new ActivityDTO(mAct.getId(), mAct.getName(), mAct.getDistance(), mAct.getDuration(), mAct.getDate(), "ManualActivity");
                activitiesDTO.add(actDTO);
            }
            else if (activity instanceof RunningSessionResult) {
                RunningSessionResult rsr = (RunningSessionResult) activity;
                ActivityDTO actDTO = new ActivityDTO(rsr.getId(), rsr.getName(),rsr.getDistance(), rsr.getDuration(), rsr.getDate(), "RunningResult");
                activitiesDTO.add(actDTO);
            }
            else if (activity instanceof StrengthSessionResult) {
                StrengthSessionResult ssr = (StrengthSessionResult) activity;
                ActivityDTO actDTO = new ActivityDTO(ssr.getId(), ssr.getName(),null, null, ssr.getDate(), "StrengthResult");
                activitiesDTO.add(actDTO);
            }
            else {
                MobilitySessionResult msr = (MobilitySessionResult) activity;
                ActivityDTO actDTO = new ActivityDTO(msr.getId(), msr.getName(),null, null, msr.getDate(), "MobilityResult");
                activitiesDTO.add(actDTO);
            }
        });
        data.put("data",activitiesDTO);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
