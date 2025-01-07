package com.example.API_Running.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STRENGTH_RESULT")
public class StrengthSessionResult extends TrainingSessionResult{

    public StrengthSessionResult() {}
}
