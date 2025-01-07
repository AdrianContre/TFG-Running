package com.example.API_Running.dtos;

public class EnrrollToAPlanDTO {
    private Long userId;

    public EnrrollToAPlanDTO() {}

    public EnrrollToAPlanDTO(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
