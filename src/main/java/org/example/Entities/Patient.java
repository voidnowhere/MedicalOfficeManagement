package org.example.Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@DiscriminatorValue("Patient")
public class Patient extends Person {
    @OneToOne
    private Record record;
    @OneToMany
    private List<Consultation> consultations;

    public Patient(String firstName, String lastName, String nic, String address, LocalDate birthday, String phoneNumber) {
        super(firstName, lastName, nic, address, birthday, phoneNumber);
    }

    public Patient() {
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public boolean isMinor() {
        return ChronoUnit.YEARS.between(getBirthday(), LocalDate.now()) < 18;
    }
}
