package com.example.API_Running.dtos;


public class RegisterRequest {
    private String name;
    private String surname;
    private String mail;
    private String username;
    private String password;
    private Integer weight;
    private Integer height;
    private Integer fcMax;
    private boolean trainer;
    private Integer experience;

    public RegisterRequest() {}

    public RegisterRequest(String name, String surname, String mail, String username, String password, Integer weight, Integer height, Integer fcMax, boolean isTrainer,Integer experience) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.fcMax = fcMax;
        this.trainer = isTrainer;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean getTrainer() {
        return this.trainer;
    }

    public void setTrainer(boolean trainer) {
        this.trainer = trainer;
    }

    public Integer getExperience() {
        return this.experience;
    }

    public void setExperience (Integer experience) {
        this.experience = experience;
    }
}
