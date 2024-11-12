package com.example.API_Running.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("REST")
public class RestSession  extends TrainingSession{

    public RestSession() {}
}
