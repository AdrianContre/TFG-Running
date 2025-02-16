package com.example.API_Running.dtos;

import com.example.API_Running.models.Runner;

public class RunnerDTO {
    private Long id;
    private String name;
    private String surname;
    private String mail;
    private String username;
    private Integer weight;
    private Integer height;
    private Integer fcMax;
    private byte[] profilePicture;
    private String userType;
    private Boolean linkPolar;

    public RunnerDTO(Runner r) {
        this.id = r.getId();
        this.name = r.getName();
        this.surname = r.getSurname();
        this.mail = r.getMail();
        this.username = r.getUsername();
        this.weight = r.getWeight();
        this.height = r.getHeight();
        this.fcMax = r.getFcMax();
        this.profilePicture = r.getProfilePicture();
        this.userType = "Runner";
        this.linkPolar = (r.getPolarAccount() != null);

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

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Boolean getLinkPolar() {
        return linkPolar;
    }

    public void setLinkPolar(Boolean linkPolar) {
        this.linkPolar = linkPolar;
    }
}
