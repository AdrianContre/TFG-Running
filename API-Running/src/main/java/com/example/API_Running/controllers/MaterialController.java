package com.example.API_Running.controllers;

import com.example.API_Running.dtos.CreateMaterialDTO;
import com.example.API_Running.services.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
