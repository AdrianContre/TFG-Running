package com.example.API_Running.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MOBILITY_RESULT")
public class MobilitySessionResult extends TrainingSessionResult{
    public MobilitySessionResult() {}
}
