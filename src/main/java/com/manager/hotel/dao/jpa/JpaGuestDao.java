package com.manager.hotel.dao.jpa;

import com.manager.hotel.dao.GuestDao;
import com.manager.hotel.exception.GuestNotFoundException;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.manager.hotel.dao.jpa.Constant.SELECT_GUESTS_BY_CRITERIA;
import static java.util.Collections.singletonList;

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
    public Guest findByPassportData(Passport passport) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Guest> query = builder.createQuery(Guest.class);
            Root<Guest> root = query.from(Guest.class);
            query.select(root).where(builder.equal(root.get("passportData"),
                    passport.getGuest())); //TODO
            return entityManager.createQuery(query).getResultList().get(0);
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
            return criteria.getPassport() != null
                    ? singletonList(findByPassportData(criteria.getPassport()))
                    : entityManager.createQuery(SELECT_GUESTS_BY_CRITERIA + criteria.getRoom().getRoomStatus(), // TODO
                    Guest.class).getResultList();
        }
    }
}
