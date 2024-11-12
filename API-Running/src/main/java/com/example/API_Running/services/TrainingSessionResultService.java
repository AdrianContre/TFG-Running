package com.example.API_Running.services;

import com.example.API_Running.models.TrainingPlan;
import com.example.API_Running.models.TrainingSession;
import com.example.API_Running.models.TrainingSessionResult;
import com.example.API_Running.models.TrainingWeek;
import com.example.API_Running.repository.TrainingPlanRepository;
import com.example.API_Running.repository.TrainingSessionResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrainingSessionResultService {
    private final TrainingSessionResultRepository trainingSessionResultRepository;
    private final TrainingPlanRepository trainingPlanRepository;

    @Autowired
    public TrainingSessionResultService(TrainingSessionResultRepository trainingSessionResultRepository, TrainingPlanRepository trainingPlanRepository) {
        this.trainingSessionResultRepository = trainingSessionResultRepository;
        this.trainingPlanRepository = trainingPlanRepository;
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

}
