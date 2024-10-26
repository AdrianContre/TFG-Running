package com.example.API_Running.services;

import com.example.API_Running.dtos.CreateMaterialDTO;
import com.example.API_Running.models.Material;
import com.example.API_Running.models.Runner;
import com.example.API_Running.repository.MaterialRepository;
import com.example.API_Running.repository.RunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Optional;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final RunnerRepository runnerRepository;

    @Autowired
    public MaterialService (MaterialRepository materialRepository, RunnerRepository runnerRepository) {
        this.materialRepository = materialRepository;
        this.runnerRepository = runnerRepository;
    }

    public ResponseEntity<Object> createMaterial (CreateMaterialDTO createMaterialDTO) {
        HashMap<String,Object> data = new HashMap<>();
        String brand = createMaterialDTO.getBrand();
        String model = createMaterialDTO.getModel();
        String description = createMaterialDTO.getDescription();
        Integer wear = createMaterialDTO.getWear();
        Long runnerId = createMaterialDTO.getRunnerId();

        Optional<Runner> query = this.runnerRepository.findById(runnerId);
        if (!query.isPresent()) {
            data.put("error", "User not found");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }
        Runner runner = query.get();
        Material material = new Material(brand,model,description,wear,runner);
        runner.addMaterial(material);
        this.materialRepository.save(material);
        this.runnerRepository.save(runner);
        data.put("data", "Material added successfully");
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> deleteMaterial(Long materialId) {
        HashMap<String,Object> data = new HashMap<>();
        Optional<Material> query = this.materialRepository.findById(materialId);
        if (!query.isPresent()) {
            data.put("error", "Material with id " + materialId + " not found");
            return new ResponseEntity<>(
                    data,
                    HttpStatus.NOT_FOUND
            );
        }
        Material material = query.get();
        this.materialRepository.delete(material);
        data.put("data", "Material with id " + materialId + " deleted successfully");
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }
}
