package com.example.API_Running.services;


import com.example.API_Running.dtos.CreateManualActivityDTO;
import com.example.API_Running.dtos.ManualActivityDTO;
import com.example.API_Running.dtos.ModifyManualActivityDTO;
import com.example.API_Running.models.*;
import com.example.API_Running.repository.ManualActivityRepository;
import com.example.API_Running.repository.MaterialRepository;
import com.example.API_Running.repository.RunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ManualActivityService {
    private final ManualActivityRepository manualActivityRepository;
    private final RunnerRepository runnerRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    public ManualActivityService (ManualActivityRepository manualActivityRepository, RunnerRepository runnerRepository, MaterialRepository materialRepository) {
        this.manualActivityRepository = manualActivityRepository;
        this.runnerRepository = runnerRepository;
        this.materialRepository = materialRepository;
    }

    public ResponseEntity<Object> createManualActivity(CreateManualActivityDTO createManualActivityDTO)  {
        HashMap<String,Object> data = new HashMap<>();
        String name = createManualActivityDTO.getName();
        String description = createManualActivityDTO.getDescription();
        Float distance = createManualActivityDTO.getDistance();
        LocalTime duration = createManualActivityDTO.getDuration();
        Float pace = createManualActivityDTO.getPace();
        Integer fcAvg = createManualActivityDTO.getFcAvg();
        LocalDateTime date = createManualActivityDTO.getDate();
        Long runnerId = createManualActivityDTO.getRunnerId();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), runnerId)) {
            data.put("error", "You can not create activities on behalf of other users");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }

        Optional<Runner> query = this.runnerRepository.findById(runnerId);
        if (!query.isPresent()) {
            data.put("error", "Runner not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Runner runner = query.get();
        List<Long> materialsId = createManualActivityDTO.getMaterialsId();
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
        ManualActivity manualActivity = new ManualActivity(distance, duration,pace,fcAvg);
        manualActivity.setName(name);
        manualActivity.setDescription(description);
        manualActivity.setDate(date);
        manualActivity.setRunner(runner);
        manualActivity.setMaterials(materials);
        ManualActivity newManualActivity = this.manualActivityRepository.save(manualActivity);
        data.put("data", newManualActivity.getId());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteManualActivity(Long manualActivityId) {
        HashMap<String,Object> data = new HashMap<>();
        Optional<ManualActivity> query = this.manualActivityRepository.findById(manualActivityId);
        if (!query.isPresent()) {
            data.put("error", "Manual activity not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        ManualActivity manualActivity = query.get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), manualActivity.getRunner().getId())) {
            data.put("error", "You can not delete an activity that is not yours");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }

        Set<Material> mats = manualActivity.getMaterials();
        mats.stream().forEach(mat -> {
            Float actWear = mat.getWear();
            actWear = actWear - manualActivity.getDistance();
            mat.setWear(actWear);
        });


        this.manualActivityRepository.delete(manualActivity);
        data.put("data", "Manual activity deleted successfully");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> uploadRoute(Long manualActivityId, MultipartFile route) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<ManualActivity> query = this.manualActivityRepository.findById(manualActivityId);
        if (!query.isPresent()) {
            data.put("error", "Manual activity not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), query.get().getRunner().getId())) {
            data.put("error", "You can not upload a route in a activity that is not yours");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        try {
            byte[] routeBytes = route.getBytes();
            ManualActivity m = query.get();
            m.setRoute(routeBytes);
            this.manualActivityRepository.save(m);
            data.put("data", "Route set properly");
            return new ResponseEntity<>(data,HttpStatus.OK);
        }
        catch(IOException e) {
            data.put("error", "Error uploading the route");
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> getManualActivity(Long manualActId) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<ManualActivity> query = this.manualActivityRepository.findById(manualActId);
        if (!query.isPresent()) {
            data.put("error", "Manual activity not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        ManualActivity manualActivity = query.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), manualActivity.getRunner().getId())) {
            data.put("error", "You can not get other runner's activities");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        ManualActivityDTO info = new ManualActivityDTO(manualActivity);
        data.put("data", info);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    public ResponseEntity<Object> updateManualActivity(Long manualActivityId, ModifyManualActivityDTO modifyManualActivity) {
        HashMap<String,Object> data = new HashMap<>();
        Optional<ManualActivity> query = this.manualActivityRepository.findById(manualActivityId);
        if (!query.isPresent()) {
            data.put("error", "Manual activity not found");
        }
        ManualActivity mAct = query.get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), mAct.getRunner().getId())) {
            data.put("error", "You can not update other runner's activities");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }

        Set<Material> mats = mAct.getMaterials();
        mats.stream().forEach(mat -> {
            Float actWear = mat.getWear();
            actWear = actWear - mAct.getDistance();
            mat.setWear(actWear);
        });

        mAct.setName(modifyManualActivity.getName());
        mAct.setDescription(modifyManualActivity.getDescription());
        mAct.setDistance(modifyManualActivity.getDistance());
        mAct.setDuration(modifyManualActivity.getDuration());
        mAct.setPace(modifyManualActivity.getPace());
        mAct.setFcAvg(modifyManualActivity.getFcAvg());
        mAct.setDate(modifyManualActivity.getDate());

        List<Long> materialsId = modifyManualActivity.getMaterialsId();
        Set<Material> materials = new HashSet<>();
        materialsId.stream().forEach(materialId -> {
            Optional<Material> query_mat = this.materialRepository.findById(materialId);
            if (query_mat.isPresent()) {
                Material mat = query_mat.get();
                mat.addMileage(modifyManualActivity.getDistance());
                this.materialRepository.save(mat);
                materials.add(mat);
            }
        });
        mAct.setMaterials(materials);
        this.manualActivityRepository.save(mAct);
        data.put("data", "Manual activity edited properly");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
