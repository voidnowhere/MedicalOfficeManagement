package org.example.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Doctor extends Person {
    private String password;
    @OneToMany
    private List<Consultation> consultations;

    public Doctor(String firstName, String lastName, String nic, String address, LocalDate birthday, String phoneNumber, String password) {
        super(firstName, lastName, nic, address, birthday, phoneNumber);
        this.password = password;
    }

    public Doctor() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
