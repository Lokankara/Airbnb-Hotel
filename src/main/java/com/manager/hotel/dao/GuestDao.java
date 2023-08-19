package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.exception.GuestNotFoundException;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.manager.hotel.dao.Constant.FETCH_GRAPH;
import static com.manager.hotel.dao.Constant.SELECT_ALL_GUESTS;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GuestDao {

    @PersistenceUnit
    private final EntityManagerFactory factory;

    public List<Guest> findAll() {
        try (EntityManager entityManager = factory.createEntityManager()) {
            EntityGraph<?> entityGraph = entityManager.getEntityGraph("guest-entity-graph");
            TypedQuery<Guest> query = entityManager.createQuery(SELECT_ALL_GUESTS, Guest.class);
            query.setHint(FETCH_GRAPH, entityGraph);
            return query.getResultList();
        }
    }

    public List<Guest> findByPassportData(String passportData) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Guest> query = builder.createQuery(Guest.class);
            Root<Guest> root = query.from(Guest.class);
            query.select(root)
                    .where(builder.equal(root.get("passportData"),
                            passportData));
            return entityManager.createQuery(query).getResultList();
        }
    }

    public List<Guest> findByDepartureDate(LocalDateTime now) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Guest> query = criteriaBuilder.createQuery(Guest.class);
            Root<Guest> root = query.from(Guest.class);
            query.select(root).where(criteriaBuilder.lessThanOrEqualTo(
                    root.get("departureDate"), now));
            return entityManager.createQuery(query).getResultList();
        }
    }

    public Guest getById(Long id) {
        return findById(id).orElseThrow(() ->
                new GuestNotFoundException(
                        "Guest not found with ID " + id));
    }

    public Guest save(Guest guest) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.persist(guest);
                transaction.commit();
                return guest;
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw new PersistenceException(e.getMessage());
            }
        }
    }

    public Optional<Guest> findById(Long guestId) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            Guest guest = entityManager.find(Guest.class, guestId);
            return Optional.ofNullable(guest);
        }
    }
}
