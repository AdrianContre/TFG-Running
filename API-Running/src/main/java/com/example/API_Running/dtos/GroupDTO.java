package com.example.API_Running.dtos;

public class GroupDTO {
    private Long id;
    private String name;
    private String trainerName;

    public GroupDTO(Long id, String name, String trainerName) {
        this.id = id;
        this.name = name;
        this.trainerName = trainerName;
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

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }
}
