package com.example.API_Running.dtos;

import com.example.API_Running.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrainingWeekDTO {
    private Long id;
    private List<SessionDetailsDTO> sessions;

    public TrainingWeekDTO (TrainingWeek trainingWeek) {
        this.id = trainingWeek.getId();
        this.sessions = buildSessionsList(trainingWeek.getSessions());
    }

    public List<SessionDetailsDTO> buildSessionsList(List<TrainingSession> sessionList) {
        List<SessionDetailsDTO> list = new ArrayList<>();
        Collections.sort(sessionList, Comparator.comparingInt(TrainingSession::getDay));
        sessionList.stream().forEach(ses -> {
            if (ses == null) {
                list.add(null);
            }
            else if (ses instanceof RunningSession) {
                SessionDetailsDTO s = new SessionDetailsDTO(ses.getId(), ses.getName(), ses.getDescription(),"running", ((RunningSession) ses).getType(),((RunningSession) ses).getDistance(), ((RunningSession) ses).getDuration());
                list.add(s);
            }
            else if (ses instanceof StrengthSession) {
                SessionDetailsDTO s = new SessionDetailsDTO(ses.getId(), ses.getName(), ses.getDescription(), "strength", null, null, null);
                list.add(s);
            }
            else if (ses instanceof MobilitySession) {
                SessionDetailsDTO s = new SessionDetailsDTO(ses.getId(), ses.getName(), ses.getDescription(), "mobility", null, null, null);
                list.add(s);
            }
            else {
                SessionDetailsDTO s = new SessionDetailsDTO(ses.getId(), ses.getName(), ses.getDescription(), "rest", null, null, null);
                list.add(s);
            }

        });
        return list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<SessionDetailsDTO> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionDetailsDTO> sessions) {
        this.sessions = sessions;
    }
}
