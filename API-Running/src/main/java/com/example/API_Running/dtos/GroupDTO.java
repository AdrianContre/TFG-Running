package com.example.API_Running.dtos;

public class GroupDTO {
    private String name;
    private String trainerName;

    public GroupDTO(String name, String trainerName) {
        this.name = name;
        this.trainerName = trainerName;
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
