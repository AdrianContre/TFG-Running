package com.example.API_Running.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MOBILITY")
public class MobilitySession extends TrainingSession{

    public MobilitySession() {}
}
