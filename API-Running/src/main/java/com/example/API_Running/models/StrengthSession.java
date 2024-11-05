package com.example.API_Running.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("STRENGTH")
public class StrengthSession  extends TrainingSession{

    public StrengthSession() {}
}
