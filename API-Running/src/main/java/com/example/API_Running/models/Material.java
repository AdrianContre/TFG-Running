package com.example.API_Running.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Material")
public class Material {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="brand",nullable = false)
    private String brand;

    @Column(name="model",nullable = false)
    private String model;

    @Column(name="description",nullable = false)
    private String description;

    @Column(name="wear",nullable = false)
    private Integer wear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "runner_id")
    private Runner runner;
}
