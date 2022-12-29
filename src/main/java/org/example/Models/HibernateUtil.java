package org.example.Models;

import org.example.Entities.*;
import org.example.Entities.Record;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(Doctor.class);
        configuration.addAnnotatedClass(Receptionist.class);
        configuration.addAnnotatedClass(Patient.class);
        configuration.addAnnotatedClass(TypeConsultation.class);
        configuration.addAnnotatedClass(Consultation.class);
        configuration.addAnnotatedClass(Record.class);
        configuration.addAnnotatedClass(Document.class);
        configuration.addAnnotatedClass(Prescription.class);
        configuration.addAnnotatedClass(MedicalCertificate.class);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(serviceRegistryBuilder.build());
    }
}