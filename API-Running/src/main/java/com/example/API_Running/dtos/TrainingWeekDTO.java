package com.example.API_Running.dtos;

import com.example.API_Running.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TrainingWeekDTO {
    private Long id;
    private List<SessionDTO> sessions;

    public TrainingWeekDTO (TrainingWeek trainingWeek) {
        this.id = trainingWeek.getId();
        this.sessions = buildSessionsList(trainingWeek.getSessions());
    }

    public List<SessionDTO> buildSessionsList(List<TrainingSession> sessionList) {
        List<SessionDTO> list = new ArrayList<>();
        Collections.sort(sessionList, Comparator.comparingInt(TrainingSession::getDay));
        sessionList.stream().forEach(ses -> {
            if (ses == null) {
                list.add(null);
            }
            else if (ses instanceof RunningSession) {
                SessionDTO s = new SessionDTO(ses.getName(), ses.getDescription(),"running", ((RunningSession) ses).getType(),((RunningSession) ses).getDistance(), ((RunningSession) ses).getDuration());
                list.add(s);
            }
            else if (ses instanceof StrengthSession) {
                SessionDTO s = new SessionDTO(ses.getName(), ses.getDescription(), "strength", null, null, null);
                list.add(s);
            }
            else if (ses instanceof MobilitySession) {
                SessionDTO s = new SessionDTO(ses.getName(), ses.getDescription(), "mobility", null, null, null);
                list.add(s);
            }
            else {
                SessionDTO s = new SessionDTO(ses.getName(), ses.getDescription(), "rest", null, null, null);
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

    public List<SessionDTO> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionDTO> sessions) {
        this.sessions = sessions;
    }
}
