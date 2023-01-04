package org.example.Models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class EntityManagerInstance {
    public static EntityManager getNewInstance() {
        return Persistence.createEntityManagerFactory("default").createEntityManager();
    }
}
