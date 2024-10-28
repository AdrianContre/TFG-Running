package com.example.API_Running.services;


import com.example.API_Running.dtos.CreateManualActivityDTO;
import com.example.API_Running.models.ManualActivity;
import com.example.API_Running.models.Material;
import com.example.API_Running.models.Runner;
import com.example.API_Running.repository.ManualActivityRepository;
import com.example.API_Running.repository.MaterialRepository;
import com.example.API_Running.repository.RunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<Object> createManualActivity(CreateManualActivityDTO createManualActivityDTO) {
        HashMap<String,Object> data = new HashMap<>();
        String name = createManualActivityDTO.getName();
        String description = createManualActivityDTO.getDescription();
        Float distance = createManualActivityDTO.getDistance();
        LocalTime duration = createManualActivityDTO.getDuration();
        Float pace = createManualActivityDTO.getPace();
        Integer fcAvg = createManualActivityDTO.getFcAvg();
        Long runnerId = createManualActivityDTO.getRunnerId();
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

        if (createManualActivityDTO.getRoute() != null) {
            manualActivity.setRoute(createManualActivityDTO.getRoute());
        }
        manualActivity.setName(name);
        manualActivity.setDescription(description);
        manualActivity.setRunner(runner);
        manualActivity.setMaterials(materials);
        this.manualActivityRepository.save(manualActivity);
        data.put("data", "Activity created properly");

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
