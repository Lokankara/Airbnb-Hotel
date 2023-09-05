package com.manager.hotel.dao.jpa;

import com.manager.hotel.dao.BookingDao;
import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.model.entity.Guest;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Subgraph;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static com.manager.hotel.dao.jpa.Constant.FETCH_GRAPH;
import static com.manager.hotel.dao.jpa.Constant.SELECT_ALL_ORDERS;
import static com.manager.hotel.dao.jpa.Constant.SELECT_BOOKING_BY_ROOM_ID;
import static com.manager.hotel.dao.jpa.Constant.SELECT_BY_IDS;
import static com.manager.hotel.dao.jpa.Constant.SELECT_ORDERS_BY_IDS;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JpaBookingDao extends BookingDao {

    @PersistenceUnit
    private final EntityManagerFactory factory;

    public Booking getBookingByRoomId(Long roomId) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            EntityGraph<Booking> entityGraph = entityManager.createEntityGraph(Booking.class);
            entityGraph.addAttributeNodes("guest");
            Subgraph<Guest> guestSubgraph = entityGraph.addSubgraph("guest");
            guestSubgraph.addAttributeNodes("passport");
            guestSubgraph.addAttributeNodes("bookings");
            return entityManager
                    .createQuery(SELECT_BOOKING_BY_ROOM_ID, Booking.class)
                    .setParameter("roomId", roomId)
                    .setHint(FETCH_GRAPH, entityGraph)
                    .getSingleResult();
        }
    }

    public List<Booking> findLatestDeals() {
        try (EntityManager entityManager = factory.createEntityManager()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Booking> query = builder.createQuery(Booking.class);
            Root<Booking> root = query.from(Booking.class);

            ParameterExpression<Date> fromDateParam = builder.parameter(Date.class, "fromDate");
            query.select(root).where(builder.or(
                    builder.greaterThanOrEqualTo(root.get("departure"), fromDateParam),
                    builder.greaterThanOrEqualTo(root.get("checkInDate"), fromDateParam),
                    builder.greaterThanOrEqualTo(root.get("checkOutDate"), fromDateParam)
            ));
            TypedQuery<Booking> typedQuery = entityManager.createQuery(query);
            typedQuery.setParameter("fromDate", new Date());
            return typedQuery.getResultList();
        }
    }

    @Override
    public List<Booking> findAll() {
        try (EntityManager entityManager = factory
                .createEntityManager()) {
            List<Long> guestIds = entityManager
                    .createQuery(SELECT_ALL_ORDERS, Long.class)
                    .getResultList();

            EntityGraph<Guest> guestGraph = entityManager
                    .createEntityGraph(Guest.class);
            guestGraph.addAttributeNodes("rooms", "passport");

            TypedQuery<Guest> guestQuery = entityManager
                    .createQuery(SELECT_BY_IDS, Guest.class);
            guestQuery.setParameter("ids", guestIds);
            guestQuery.setHint(FETCH_GRAPH, guestGraph);

            TypedQuery<Booking> bookingQuery =
                    entityManager.createQuery(SELECT_ORDERS_BY_IDS, Booking.class);
            bookingQuery.setParameter("ids", guestIds);

            List<Booking> bookings = bookingQuery.getResultList();
            for (Booking booking : bookings) {
                Guest guest = guestQuery
                        .getResultList().stream()
                        .filter(g -> g.getId().equals(booking.getGuest().getId()))
                        .findFirst()
                        .orElse(null);
                booking.setGuest(guest);
            }
            return bookings;
        }
    }
}
