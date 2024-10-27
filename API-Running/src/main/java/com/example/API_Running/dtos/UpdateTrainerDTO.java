package com.example.API_Running.dtos;

public class UpdateTrainerDTO {
    private String name;
    private String surname;
    private String username;
    private String mail;
    private Integer height;
    private Integer weight;
    private Integer fcMax;
    private Integer experience;

    public UpdateTrainerDTO(String name, String surname, String username, String mail, Integer height, Integer weight, Integer fcMax, Integer experience) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.mail = mail;
        this.height = height;
        this.weight = weight;
        this.fcMax = fcMax;
        this.experience = experience;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getFcMax() {
        return fcMax;
    }

    public void setFcMax(Integer fcMax) {
        this.fcMax = fcMax;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}
