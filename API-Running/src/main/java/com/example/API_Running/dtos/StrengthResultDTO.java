package com.example.API_Running.dtos;

import com.example.API_Running.models.Material;
import com.example.API_Running.models.StrengthSessionResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StrengthResultDTO {
    private Long id;
    private String planName;
    private String name;
    private String description;
    private LocalDateTime date;
    private Integer effort;
    private List<String> materials;

    public StrengthResultDTO(StrengthSessionResult ssr, String planName) {
        this.id = ssr.getId();
        this.planName = planName;
        this.name = ssr.getName();
        this.description = ssr.getDescription();
        this.date = ssr.getDate();
        this.effort = ssr.getEffort();

        this.materials = new ArrayList<>();
        Set<Material> materials1 = ssr.getMaterials();
        materials1.forEach(mat -> {
            String nameMat = mat.getBrand() + " " + mat.getModel();
            this.materials.add(nameMat);
        });
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getEffort() {
        return effort;
    }

    public void setEffort(Integer effort) {
        this.effort = effort;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }
}
