package com.manager.hotel.dao.jpa;

import com.manager.hotel.dao.BookingDao;
import com.manager.hotel.model.entity.Booking;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.manager.hotel.dao.jpa.Constant.SELECT_LATEST;

@Repository
@RequiredArgsConstructor
public class JpaBookingDao extends BookingDao {

    @PersistenceUnit
    private final EntityManagerFactory factory;

    public List<Booking> findLatestDeals(
            final Timestamp fromDate) {
        try (EntityManager entityManager =
                     factory.createEntityManager()) {
            return entityManager
                    .createQuery(SELECT_LATEST, Booking.class)
                    .setParameter("fromDate", fromDate)
                    .getResultList();
        }
    }

    @Override
    public List<Booking> findAll() {
        try (EntityManager entityManager = factory.createEntityManager()) {
            EntityGraph<Booking> entityGraph = entityManager.createEntityGraph(Booking.class);
            entityGraph.addAttributeNodes("guest");
            TypedQuery<Booking> query = entityManager.createQuery("SELECT b FROM Booking b", Booking.class);
            query.setHint("javax.persistence.loadgraph", entityGraph);
            return query.getResultList();
        }
    }
}
