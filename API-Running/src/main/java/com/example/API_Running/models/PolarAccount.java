package com.example.API_Running.models;


import jakarta.persistence.*;


@Entity
@Table(name="PolarAccount")
public class PolarAccount {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String accessToken;
    private String token_type;
    private Integer polarUserId;
    private Integer tokenExpiry;

    @OneToOne
    private User user;

    public PolarAccount() {
    }

    public PolarAccount(String accessToken, String token_type, Integer polarUserId, Integer tokenExpiry, User user) {
        this.accessToken = accessToken;
        this.token_type = token_type;
        this.polarUserId = polarUserId;
        this.tokenExpiry = tokenExpiry;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Integer getPolarUserId() {
        return polarUserId;
    }

    public void setPolarUserId(Integer polarUserId) {
        this.polarUserId = polarUserId;
    }

    public Integer getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(Integer tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
