package com.example.API_Running.services;

import com.example.API_Running.dtos.CreateMaterialDTO;
import com.example.API_Running.dtos.MaterialDTO;
import com.example.API_Running.dtos.ModifyMaterialDTO;
import com.example.API_Running.models.*;
import com.example.API_Running.repository.ActivityRepository;
import com.example.API_Running.repository.MaterialRepository;
import com.example.API_Running.repository.RunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;
    private final RunnerRepository runnerRepository;
    private final ActivityRepository activityRepository;

    @Autowired
    public MaterialService (MaterialRepository materialRepository, RunnerRepository runnerRepository, ActivityRepository activityRepository) {
        this.materialRepository = materialRepository;
        this.runnerRepository = runnerRepository;
        this.activityRepository = activityRepository;
    }

    public ResponseEntity<Object> createMaterial (CreateMaterialDTO createMaterialDTO) {
        HashMap<String,Object> data = new HashMap<>();
        String brand = createMaterialDTO.getBrand();
        String model = createMaterialDTO.getModel();
        String description = createMaterialDTO.getDescription();
        Float wear = createMaterialDTO.getWear();
        Long runnerId = createMaterialDTO.getRunnerId();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), runnerId)) {
            data.put("error", "You can not create materials on behalf other users");
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
        Material material = new Material(brand,model,description,wear,runner);
        runner.addMaterial(material);
        Material newMat = this.materialRepository.save(material);
        this.runnerRepository.save(runner);
        data.put("data", newMat.getId());
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), material.getRunner().getId())) {
            data.put("error", "You can not delete materials on behalf other users");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        List<Activity> activitiesWithMaterial = activityRepository.findAllByMaterialsContains(material);

        for (Activity activity : activitiesWithMaterial) {
            activity.getMaterials().remove(material);
            activityRepository.save(activity);
        }

        this.materialRepository.delete(material);
        data.put("data", "Material with id " + materialId + " deleted successfully");
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> getUserMaterial(Long runnerId) {
        HashMap<String,Object> data = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), runnerId)) {
            data.put("error", "You can not get the materials of other runners");
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
        List<Material> materials = this.materialRepository.findMaterialsByRunnerId(runnerId);
        List<MaterialDTO> materialDTOS = new ArrayList<>();
        materials.stream().forEach(material -> {
            MaterialDTO m = new MaterialDTO(material);
            materialDTOS.add(m);
        });
        data.put("data", materialDTOS);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<Object> getMaterialById(Long materialId) {
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), material.getRunner().getId())) {
            data.put("error", "You can not get material from other runners");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        MaterialDTO materialDTO = new MaterialDTO(material);
        data.put("data", materialDTO);
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> editMaterial(Long materialId, ModifyMaterialDTO modifyMaterialDTO) {
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), material.getRunner().getId())) {
            data.put("error", "You can not edit materials from other runners");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        material.setBrand(modifyMaterialDTO.getBrand());
        material.setModel(modifyMaterialDTO.getModel());
        material.setDescription(modifyMaterialDTO.getDescription());
        material.setWear(modifyMaterialDTO.getWear());
        this.materialRepository.save(material);
        MaterialDTO mDTO = new MaterialDTO(material);
        data.put("data", mDTO);
        return new ResponseEntity<>(
                data,
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> uploadPhoto(Long materialId, MultipartFile photo) {
        HashMap<String, Object> data = new HashMap<>();
        Optional<Material> query = this.materialRepository.findById(materialId);
        if (!query.isPresent()) {
            data.put("error", "Material not found");
            return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImplementation u = (UserDetailsImplementation) authentication.getPrincipal();
        User userAuth = u.getUser();
        if (!Objects.equals(userAuth.getId(), query.get().getRunner().getId())) {
            data.put("error", "You can not upload photo to other runner materials");
            return new ResponseEntity<>(data, HttpStatus.FORBIDDEN);
        }
        try {
            byte[] photoBytes = photo.getBytes();
            Material mat = query.get();
            mat.setPhoto(photoBytes);
            this.materialRepository.save(mat);
            data.put("data", "Photo uploaded properly");
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        catch(IOException e) {
            data.put("error", "Error uploading the photo");
            return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
