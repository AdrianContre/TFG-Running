package com.example.API_Running.controllers;

import com.example.API_Running.dtos.CreateMaterialDTO;
import com.example.API_Running.dtos.ModifyMaterialDTO;
import com.example.API_Running.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/materials")
public class MaterialController {

    private final MaterialService materialService;

    @Autowired
    public MaterialController (MaterialService materialService) {
        this.materialService = materialService;
    }

    @PostMapping
    public ResponseEntity<Object> createMaterial (@RequestBody CreateMaterialDTO createMaterialDTO) {
        return this.materialService.createMaterial(createMaterialDTO);
    }

    @DeleteMapping(path = "/{materialId}")
    public  ResponseEntity<Object> deleteMaterial (@PathVariable Long materialId) {
        return this.materialService.deleteMaterial(materialId);
    }

    @GetMapping(path="/runners/{runnerId}")
    public ResponseEntity<Object> getUserMaterial(@PathVariable Long runnerId) {
        return this.materialService.getUserMaterial(runnerId);
    }

    @GetMapping(path="/{materialId}")
    public ResponseEntity<Object> getMaterial(@PathVariable Long materialId) {
        return this.materialService.getMaterialById(materialId);
    }

    @PutMapping(path="/{materialId}")
    public ResponseEntity<Object> editMaterial(@PathVariable Long materialId, @RequestBody ModifyMaterialDTO modifyMaterialDTO) {
        return this.materialService.editMaterial(materialId, modifyMaterialDTO);
    }

    @PutMapping(path="/{materialId}/photo")
    public ResponseEntity<Object> uploadPhoto(@PathVariable Long materialId, @RequestParam MultipartFile photo) {
        return this.materialService.uploadPhoto(materialId, photo);
    }
}
