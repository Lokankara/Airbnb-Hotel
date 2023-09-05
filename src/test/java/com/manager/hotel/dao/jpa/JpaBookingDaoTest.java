package com.manager.hotel.dao.jpa;

import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.model.entity.Guest;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Subgraph;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.manager.hotel.dao.jpa.Constant.FETCH_GRAPH;
import static com.manager.hotel.dao.jpa.Constant.SELECT_ALL_ORDERS;
import static com.manager.hotel.dao.jpa.Constant.SELECT_BOOKING_BY_ROOM_ID;
import static com.manager.hotel.dao.jpa.Constant.SELECT_BY_IDS;
import static com.manager.hotel.dao.jpa.Constant.SELECT_ORDERS_BY_IDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JpaBookingDaoTest {

    private JpaBookingDao bookingDao;
    @Mock
    private EntityManagerFactory factory;
    @Mock
    private EntityManager entityManager;
    @Mock
    private TypedQuery<Booking> query;
    @Mock
    private EntityGraph<Booking> entityGraph;
    @Mock
    private Subgraph<Object> guestSubgraph;
    @Mock
    private TypedQuery<Booking> bookingQuery;
    @Mock
    private TypedQuery<Guest> guestQuery;
    @Mock
    private EntityGraph<Guest> guestEntityGraph;
    @Mock
    private TypedQuery<Long> longQuery;
    @Mock
    CriteriaBuilder criteriaBuilder;
    @Mock
    CriteriaQuery<Booking> criteriaQuery;
    @Mock
    Root<Booking> root;
    @Mock
    ParameterExpression<Date> fromDateParam;
    private List<Long> guestIds;

    @BeforeEach
    public void setUp() {
        guestIds = Arrays.asList(1L, 2L);
        MockitoAnnotations.openMocks(this);
        bookingDao = new JpaBookingDao(factory);
    }

    @Test
    @DisplayName("Given a room ID, When getting a Booking by room ID, Then the Booking for that room should be returned")
    void testGetBookingByRoomId() {
        Long roomId = 1L;
        Booking expectedBooking = new Booking();
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.createEntityGraph(Booking.class)).thenReturn(entityGraph);
        when(entityGraph.addSubgraph("guest")).thenReturn(guestSubgraph);
        when(entityManager.createQuery(SELECT_BOOKING_BY_ROOM_ID, Booking.class)).thenReturn(query);
        when(query.setParameter("roomId", roomId)).thenReturn(query);
        when(query.setHint(FETCH_GRAPH, entityGraph)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(expectedBooking);
        Booking actualBooking = bookingDao.getBookingByRoomId(roomId);

        assertEquals(expectedBooking, actualBooking);
        verify(factory).createEntityManager();
        verify(entityManager).createEntityGraph(Booking.class);
        verify(entityManager).createQuery(any(String.class), eq(Booking.class));
        verify(entityGraph).addSubgraph(any(String.class));
        verify(query).setParameter("roomId", roomId);
        verify(query).getSingleResult();
    }

    @Test
    @DisplayName("When finding all Bookings, Then a list of all Bookings should be returned")
    void testFindAll() {
        List<Booking> expectedBookings = Arrays.asList(new Booking(), new Booking());
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.createQuery(SELECT_ALL_ORDERS, Long.class)).thenReturn(longQuery);
        when(longQuery.getResultList()).thenReturn(guestIds);
        when(entityManager.createEntityGraph(Guest.class)).thenReturn(guestEntityGraph);
        when(entityManager.createQuery(SELECT_BY_IDS, Guest.class)).thenReturn(guestQuery);
        when(guestQuery.setParameter("ids", guestIds)).thenReturn(guestQuery);
        when(guestQuery.setHint(FETCH_GRAPH, guestEntityGraph)).thenReturn(guestQuery);
        when(entityManager.createQuery(SELECT_ORDERS_BY_IDS, Booking.class)).thenReturn(bookingQuery);
        when(bookingQuery.setParameter("ids", guestIds)).thenReturn(bookingQuery);
        when(bookingQuery.getResultList()).thenReturn(expectedBookings);
        List<Booking> actualBookings = bookingDao.findAll();

        assertEquals(expectedBookings, actualBookings);
        verify(factory).createEntityManager();
        verify(entityManager).createQuery(SELECT_ALL_ORDERS, Long.class);
        verify(longQuery).getResultList();
        verify(entityManager).createEntityGraph(Guest.class);
        verify(entityManager).createQuery(SELECT_BY_IDS, Guest.class);
        verify(guestQuery).setParameter("ids", guestIds);
        verify(guestQuery).setHint(FETCH_GRAPH, guestEntityGraph);
        verify(entityManager).createQuery(SELECT_ORDERS_BY_IDS, Booking.class);
        verify(bookingQuery).setParameter("ids", guestIds);
        verify(bookingQuery).getResultList();
    }

    @Test
    @DisplayName("When finding the latest deals, Then a list of the latest Booking deals should be returned")
    void testFindLatestDeals() {
        List<Booking> expectedBookings = Arrays.asList(new Booking(), new Booking());
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Booking.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Booking.class)).thenReturn(root);
        when(criteriaBuilder.parameter(Date.class, "fromDate")).thenReturn(fromDateParam);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        Predicate departurePredicate = mock(Predicate.class);
        Predicate checkInPredicate = mock(Predicate.class);
        Predicate checkOutPredicate = mock(Predicate.class);
        when(criteriaBuilder.greaterThanOrEqualTo(root.get("departure"), fromDateParam)).thenReturn(departurePredicate);
        when(criteriaBuilder.greaterThanOrEqualTo(root.get("checkInDate"), fromDateParam)).thenReturn(checkInPredicate);
        when(criteriaBuilder.greaterThanOrEqualTo(root.get("checkOutDate"), fromDateParam)).thenReturn(checkOutPredicate);
        Predicate orPredicate = mock(Predicate.class);
        when(criteriaBuilder.or(departurePredicate, checkInPredicate, checkOutPredicate)).thenReturn(orPredicate);
        when(criteriaQuery.where(orPredicate)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(bookingQuery);
        when(bookingQuery.setParameter("fromDate", new Date())).thenReturn(bookingQuery);
        when(bookingQuery.getResultList()).thenReturn(expectedBookings);
        List<Booking> actualBookings = bookingDao.findLatestDeals();

        assertEquals(expectedBookings, actualBookings);
        verify(factory).createEntityManager();
        verify(entityManager).getCriteriaBuilder();
        verify(criteriaBuilder).createQuery(Booking.class);
        verify(criteriaQuery).from(Booking.class);
        verify(criteriaBuilder).parameter(Date.class, "fromDate");
        verify(criteriaQuery).select(root);
        verify(bookingQuery).getResultList();
    }
}
