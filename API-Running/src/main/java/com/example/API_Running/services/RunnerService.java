package com.example.API_Running.services;

import com.example.API_Running.dtos.RunnerDTO;
import com.example.API_Running.dtos.UpdateRunnerDTO;
import com.example.API_Running.dtos.UserZonesDTO;
import com.example.API_Running.models.*;
import com.example.API_Running.repository.ActivityRepository;
import com.example.API_Running.repository.RunnerRepository;
import com.example.API_Running.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class RunnerService {

    private final RunnerRepository runnerRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final JwtService jwtService;

    public RunnerService (RunnerRepository runnerRepository, UserRepository userRepository, ActivityRepository activityRepository, JwtService jwtService) {
        this.runnerRepository = runnerRepository;
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
        this.jwtService = jwtService;
    }

    public ResponseEntity<Object> updateRunner(Long runnerId, UpdateRunnerDTO updateRunnerDTO) {
        HashMap<String,Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), runnerId)) {
            data.put("error", "You can not modify other runner profile that is not you");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        Optional<Runner> query = this.runnerRepository.findById(runnerId);
        if (query.isPresent()) {
            Runner runner = query.get();
            Optional<User> username_query = this.userRepository.findByUsername(updateRunnerDTO.getUsername());
            if (username_query.isPresent() && !Objects.equals(username_query.get().getId(), runnerId)) {
                data.put("error", "There's another user with this username");
                return new ResponseEntity<>(data, HttpStatus.CONFLICT);
            }
            Optional<User> mail_query = this.userRepository.findByMail(updateRunnerDTO.getMail());
            if (mail_query.isPresent() && !Objects.equals(mail_query.get().getId(), runnerId)) {
                data.put("error", "There's another user with this mail");
                return new ResponseEntity<>(data, HttpStatus.CONFLICT);
            }
            runner.setName(updateRunnerDTO.getName());
            runner.setSurname(updateRunnerDTO.getSurname());
            runner.setUsername(updateRunnerDTO.getUsername());
            runner.setMail(updateRunnerDTO.getMail());
            runner.setHeight(updateRunnerDTO.getHeight());
            runner.setWeight(updateRunnerDTO.getWeight());
            runner.setFcMax(updateRunnerDTO.getFcMax());
            this.runnerRepository.save(runner);
            RunnerDTO info = new RunnerDTO(runner);
            data.put("data", info);

            UserDetailsImplementation updatedUserDetails = new UserDetailsImplementation(runner);
            String newToken = jwtService.getToken(updatedUserDetails);
            data.put("token", newToken);

            return new ResponseEntity<>(
                    data,
                    HttpStatus.OK
            );
        }
        data.put("error", "Runner with id " + runnerId + " not found");
        return new ResponseEntity<>(
                data,
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<Object> getZones(Long runnerId) {
        HashMap<String,Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), runnerId)) {
            data.put("error", "You can not get the training zones of other trainers");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        Optional<Runner> query = this.runnerRepository.findById(runnerId);
        if (!query.isPresent()) {
            data.put("error", "User not found");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }
        Runner runner = query.get();
        ArrayList<Integer> zones = runner.getZones();
        UserZonesDTO zonesDTO = new UserZonesDTO(zones);
        data.put("data", zonesDTO);
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> getRunnerStats(Long runnerId) {
        HashMap<String,Object> data = new HashMap<>();
        HashMap<String, Object> info = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), runnerId)) {
            data.put("error", "You can not get the stats of other runners");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        Optional<Runner> query = this.runnerRepository.findById(runnerId);
        if (!query.isPresent()) {
            data.put("error", "The runner does not exist");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Runner runner = query.get();
        HashMap<String, Float> mostUsedMaterial = runner.getMostUsedMaterial();

        info.put("mostUsedMaterial", mostUsedMaterial);

        List<Activity> allRunnerAct = this.activityRepository.findActivitiesByRunnerId(runnerId);
        List<Integer> distribution = new ArrayList<>(Arrays.asList(0, 0, 0));
        for (Activity activity : allRunnerAct) {
            if (activity instanceof ManualActivity || activity instanceof RunningSessionResult) {
                distribution.set(0, distribution.get(0) + 1);
            }
            else if (activity instanceof StrengthSessionResult) {
                distribution.set(1, distribution.get(1) + 1);
            }
            else {
                distribution.set(2, distribution.get(2) + 1);
            }
        }

        info.put("distribution", distribution);

        LocalDateTime startDate = LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .atStartOfDay();

        LocalDateTime endDate = LocalDate.now()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                .atTime(23, 59, 59);

        List<Activity> weekActivities = this.activityRepository.findByRunnerIdAndDateBetween(runnerId, startDate, endDate);
        Integer numAct  = weekActivities.size();
        Float weekMileage = (float) 0;
        for (Activity act : weekActivities) {
            if (act instanceof ManualActivity) {
                ManualActivity mAct = (ManualActivity) act;
                weekMileage += mAct.getDistance();
            }
            else if (act instanceof RunningSessionResult) {
                RunningSessionResult rsr = (RunningSessionResult) act;
                weekMileage += rsr.getDistance();
            }
        }
        info.put("weekActivities", numAct);
        info.put("weekMileage", weekMileage);

        LocalDate today = LocalDate.now();
        List<Float> last4WeeksMileage = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {
            LocalDate startOfWeek = today.minusWeeks(i).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate endOfWeek = startOfWeek.plusDays(6);

            LocalDateTime startDateTime = startOfWeek.atStartOfDay();
            LocalDateTime endDateTime = endOfWeek.atTime(23, 59, 59);

            // Consulta la distancia total en el rango semanal
            List<Activity> acts = this.activityRepository.findByRunnerIdAndDateBetween(runnerId, startDateTime, endDateTime);
            Float weekDistance = (float) 0;
            for (Activity act : acts) {
                if (act instanceof ManualActivity) {
                    ManualActivity mAct = (ManualActivity) act;
                    weekDistance += mAct.getDistance();
                }
                else if (act instanceof RunningSessionResult) {
                    RunningSessionResult rsr = (RunningSessionResult) act;
                    weekDistance += rsr.getDistance();
                }
            }
            last4WeeksMileage.add(weekDistance);
        }
        info.put("last4Weeks", last4WeeksMileage);

        info.put("plansInProgress", runner.getPlansProgress().size());

        data.put("data", info);

        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }
}
