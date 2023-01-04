package org.example.Entities;

import jakarta.persistence.Entity;

@Entity
public class Prescription extends Document {
    public Prescription(String description, Record record) {
        super(description, record);
    }

    public Prescription() {
    }
}
