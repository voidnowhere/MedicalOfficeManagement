package org.example.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2500)
    private String description;
    @OneToOne
    private Patient patient;
    @OneToMany
    private List<Document> documents;

    public Record(String description, Patient patient) {
        this.description = description;
        this.patient = patient;
    }

    public Record() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
