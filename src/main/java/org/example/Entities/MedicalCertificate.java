package org.example.Entities;

import jakarta.persistence.Entity;

@Entity
public class MedicalCertificate extends Document {
    public MedicalCertificate(String description, Record record) {
        super(description, record);
    }

    public MedicalCertificate() {
    }
}
