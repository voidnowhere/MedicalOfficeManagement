package org.example.Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("Receptionist")
public class Receptionist extends Person {
    private String password;
    private boolean isActive;
    @OneToMany
    private List<Consultation> consultations;

    public Receptionist(String firstName, String lastName, String nic, String address, LocalDate birthday, String phoneNumber, String password, boolean isActive) {
        super(firstName, lastName, nic, address, birthday, phoneNumber);
        this.password = password;
        this.isActive = isActive;
    }

    public Receptionist() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
