package com.example.API_Running.dtos;


import com.example.API_Running.models.ManualActivity;
import com.example.API_Running.models.Material;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ManualActivityDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private Float distance;
    private Float pace;
    private LocalTime duration;
    private Integer fcAvg;
    private List<String> materials;
    //private MultipartFile route;
    private byte[] route;

    public ManualActivityDTO (ManualActivity manualActivity) {
        this.id = manualActivity.getId();
        this.name = manualActivity.getName();
        this.description = manualActivity.getDescription();
        this.date = manualActivity.getDate();
        this.distance = manualActivity.getDistance();
        this.duration = manualActivity.getDuration();
        this.pace = manualActivity.getPace();
        this.fcAvg = manualActivity.getFcAvg();
        this.materials = new ArrayList<>();
        if (manualActivity.getRoute() != null) {
            //this.route = new CustomMultipartFile("route.gpx", manualActivity.getRoute());
            this.route = manualActivity.getRoute();
        }
        else {
            this.route = null;
        }
        Set<Material> materials1 = manualActivity.getMaterials();
        materials1.stream().forEach(mat -> {
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

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public Integer getFcAvg() {
        return fcAvg;
    }

    public void setFcAvg(Integer fcAvg) {
        this.fcAvg = fcAvg;
    }

    public byte[] getRoute() {
        return route;
    }

    public void setRoute(byte[] route) {
        this.route = route;
    }

    public List<String> getMaterials() {
        return materials;
    }

    public void setMaterials(List<String> materials) {
        this.materials = materials;
    }

    public Float getPace() {
        return pace;
    }

    public void setPace(Float pace) {
        this.pace = pace;
    }
}
