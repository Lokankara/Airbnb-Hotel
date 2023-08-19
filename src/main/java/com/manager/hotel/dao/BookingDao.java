package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Booking;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookingDao {

    @PersistenceUnit
    private final EntityManagerFactory factory;

    public Booking save(Booking checkOut) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.persist(checkOut);
                transaction.commit();
                return checkOut;
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new PersistenceException(e.getMessage());
            }
        }
    }
}
