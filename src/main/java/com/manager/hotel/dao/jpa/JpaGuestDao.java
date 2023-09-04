package com.manager.hotel.dao.jpa;

import com.manager.hotel.dao.GuestDao;
import com.manager.hotel.exception.GuestNotFoundException;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JpaGuestDao extends GuestDao {

    @PersistenceUnit
    private final EntityManagerFactory factory;

    @Override
    public List<Guest> findAll() {
        try (EntityManager entityManager = factory.createEntityManager()) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Guest> query = cb.createQuery(Guest.class);
            Root<Guest> root = query.from(Guest.class);
            root.fetch("passport", JoinType.LEFT);
            root.fetch("rooms", JoinType.LEFT);
            return entityManager.createQuery(query).getResultList();
        }
    }

    @Override
    public Optional<Guest> findByPassportData(Passport passport) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Guest> query = builder.createQuery(Guest.class);
            Root<Guest> root = query.from(Guest.class);
            query.select(root).where(builder.equal(root.get("passportData"), passport.getGuest()));
            List<Guest> guests = entityManager.createQuery(query).getResultList();
            return guests.isEmpty() ? Optional.empty() : Optional.of(guests.get(0));
        }
    }

    @Override
    public List<Guest> findByDepartureDate(final LocalDateTime now) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Guest> query = criteriaBuilder.createQuery(Guest.class);
            Root<Guest> root = query.from(Guest.class);
            query.select(root).where(criteriaBuilder.lessThanOrEqualTo(
                    root.get("departureDate"), now));
            return entityManager.createQuery(query).getResultList();
        }
    }

    @Override
    public Guest getById(final Long id) {
        return findById(id).orElseThrow(() ->
                new GuestNotFoundException(
                        "Guest not found with ID " + id));
    }

    @Override
    public List<Guest> findByCriteria(final Criteria criteria) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Guest> query = builder.createQuery(Guest.class);
            Root<Guest> root = query.from(Guest.class);
            Predicate predicate = builder.conjunction();

            if (criteria.getName() != null && !criteria.getName().isEmpty()) {
                predicate = builder.and(predicate, builder.like(
                        builder.lower(root.get("passportData")),
                        "%" + criteria.getName().toLowerCase() + "%"));
            }

            if (criteria.getGuestStatus() != null) {
                predicate = builder.and(predicate, builder.equal(
                        root.get("guestStatus"), criteria.getGuestStatus()));
            }

            if (criteria.getGender() != null) {
                predicate = builder.and(predicate,
                        builder.equal(root.get("gender"), criteria.getGender()));
            }

            query.where(predicate);
            return entityManager.createQuery(query).getResultList();
        }
    }

    public Guest update(Guest guest) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Guest updatedGuest = entityManager.merge(guest);
            transaction.commit();
            return updatedGuest;
        }
    }
}
