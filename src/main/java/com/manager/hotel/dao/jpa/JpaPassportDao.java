package com.manager.hotel.dao.jpa;

import com.manager.hotel.dao.PassportDao;
import com.manager.hotel.model.entity.Passport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.manager.hotel.dao.jpa.Constant.SELECT_PASSPORT;

@Repository
@RequiredArgsConstructor
public class JpaPassportDao extends PassportDao {

    @PersistenceUnit
    private final EntityManagerFactory factory;

    @Override
    public Optional<Passport> findByFirstNameAndLastName(
            final String name,
            final String surname) {
        try (EntityManager entityManager =
                     factory.createEntityManager()) {
            return entityManager.createQuery(SELECT_PASSPORT, Passport.class)
                    .setParameter("firstName", name)
                    .setParameter("lastName", surname)
                    .getResultList()
                    .stream()
                    .findFirst();
        }
    }
}
