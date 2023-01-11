package org.example.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class TypeConsultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    @OneToMany(mappedBy = "type")
    private List<Consultation> consultations;

    public TypeConsultation(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public TypeConsultation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
}
