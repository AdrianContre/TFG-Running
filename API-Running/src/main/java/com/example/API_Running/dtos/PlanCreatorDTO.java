package com.example.API_Running.dtos;

import com.example.API_Running.models.Trainer;

public class PlanCreatorDTO {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private Integer experience;

    public PlanCreatorDTO(Trainer creator) {
        this.id = creator.getId();
        this.name = creator.getName();
        this.surname = creator.getSurname();
        this.username = creator.getUsername();
        this.experience = creator.getExperience();
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}
