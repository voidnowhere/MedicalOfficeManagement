package org.example.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Consultation {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime dateTime;
    private String description;
    private boolean nextTime;
    private double price;
    @Column(columnDefinition = "boolean default false")
    private boolean isPaid;
    @Column(columnDefinition = "boolean default false")
    private boolean isCanceled;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Receptionist receptionist;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private TypeConsultation type;

    public Consultation(LocalDateTime dateTime, String description, boolean nextTime, double price, Doctor doctor, Receptionist receptionist, Patient patient, TypeConsultation type) {
        this.dateTime = dateTime;
        this.description = description;
        this.nextTime = nextTime;
        this.price = price;
        this.doctor = doctor;
        this.receptionist = receptionist;
        this.patient = patient;
        this.type = type;
    }

    public Consultation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNextTime() {
        return nextTime;
    }

    public void setNextTime(boolean nextTime) {
        this.nextTime = nextTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Receptionist getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(Receptionist receptionist) {
        this.receptionist = receptionist;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public TypeConsultation getType() {
        return type;
    }

    public void setType(TypeConsultation type) {
        this.type = type;
    }
}
