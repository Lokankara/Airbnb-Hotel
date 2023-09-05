package com.manager.hotel.dao.jpa;

import com.manager.hotel.dao.GuestDao;
import com.manager.hotel.exception.GuestNotFoundException;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;
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

import static com.manager.hotel.dao.jpa.Constant.SELECT_BY_FULL_NAME;
import static java.sql.Timestamp.valueOf;

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
                    root.get("checkOut"), now));
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
        String start = " 00:00:00";
        String end = " 23:59:59";
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
            if (criteria.getDeparture() != null) {
                String departureTimestamp = criteria.getDeparture() + end;
                predicate = builder.and(predicate,
                        builder.lessThanOrEqualTo(root.get("departure"),
                                valueOf(departureTimestamp)));
            }

            if (criteria.getCheckIn() != null && criteria.getCheckOut() != null) {
                predicate = builder.and(predicate, builder.between(
                        root.get("checkIn"),
                        valueOf(criteria.getCheckIn() + start),
                        valueOf(criteria.getCheckOut() + end)));
            } else {
                if (criteria.getCheckIn() != null) {
                    predicate = builder.and(predicate,
                            builder.greaterThanOrEqualTo(root.get("checkIn"),
                                    valueOf(criteria.getCheckIn() + start)));
                }
                if (criteria.getCheckOut() != null) {
                    predicate = builder.and(predicate,
                            builder.lessThanOrEqualTo(root.get("checkOut"),
                                    valueOf(criteria.getCheckOut() + end)));
                }
            }
            query.where(predicate);
            return entityManager.createQuery(query).getResultList();
        }
    }

    public Guest update(Guest guest) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                Guest updatedGuest = entityManager.merge(guest);
                transaction.commit();
                return updatedGuest;
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                log.error(e.getMessage());
                throw new PersistenceException("Error updating Guest");
            }
        }
    }

    @Override
    public Optional<Guest> findByFullName(String firstname, String lastname) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            TypedQuery<Guest> query = entityManager.createQuery(SELECT_BY_FULL_NAME, Guest.class);
            query.setParameter("full", firstname + " " + lastname);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
