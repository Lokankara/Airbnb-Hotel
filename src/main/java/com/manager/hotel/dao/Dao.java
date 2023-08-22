package com.manager.hotel.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;
import java.util.Optional;

public abstract class Dao<T> {
    private final Class<T> entity;
    @PersistenceUnit
    private EntityManagerFactory factory;

    Dao(Class<T> entity) {
        this.entity = entity;
    }

    public List<T> findAll() {
        try (EntityManager entityManager =
                     factory.createEntityManager()) {
            CriteriaQuery<T> query = entityManager
                    .getCriteriaBuilder()
                    .createQuery(entity);
            query.select(query.from(entity));
            return entityManager
                    .createQuery(query)
                    .getResultList();
        }
    }

    public Optional<T> findById(Long id) {
        try (EntityManager entityManager =
                     factory.createEntityManager()) {
            return Optional.ofNullable(
                    entityManager.find(entity, id));
        }
    }

    public T save(T t) {
        try (EntityManager entityManager =
                     factory.createEntityManager()) {
            EntityTransaction transaction =
                    entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.persist(t);
                transaction.commit();
                return t;
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new PersistenceException(e.getMessage());
            }
        }
    }
}
