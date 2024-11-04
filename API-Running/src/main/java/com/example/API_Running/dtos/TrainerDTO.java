package com.example.API_Running.dtos;

import com.example.API_Running.models.Runner;
import com.example.API_Running.models.Trainer;

public class TrainerDTO {
    private Long id;
    private String name;
    private String surname;
    private String mail;
    private String username;
    private Integer weight;
    private Integer height;
    private Integer fcMax;
    private byte[] profilePicture;
    private Integer experience;



    private String userType;

    public TrainerDTO(Trainer t) {
        this.id = t.getId();
        this.name = t.getName();
        this.surname = t.getSurname();
        this.mail = t.getMail();
        this.username = t.getUsername();
        this.weight = t.getWeight();
        this.height = t.getHeight();
        this.fcMax = t.getFcMax();
        this.profilePicture = t.getProfilePicture();
        this.userType = "Trainer";
        this.experience = t.getExperience();

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getFcMax() {
        return fcMax;
    }

    public void setFcMax(Integer fcMax) {
        this.fcMax = fcMax;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
}
