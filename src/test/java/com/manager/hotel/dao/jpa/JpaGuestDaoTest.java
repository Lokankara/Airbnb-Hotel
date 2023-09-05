package com.manager.hotel.dao.jpa;

import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;
import com.manager.hotel.model.enums.Gender;
import com.manager.hotel.model.enums.GuestStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.manager.hotel.dao.jpa.Constant.SELECT_BY_FULL_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JpaGuestDaoTest {
    private JpaGuestDao guestDao;
    @Mock
    private EntityManagerFactory factory;
    @Mock
    private EntityManager entityManager;
    @Mock
    private TypedQuery<Guest> guestQuery;
    @Mock
    CriteriaBuilder criteriaBuilder;
    @Mock
    CriteriaQuery<Guest> criteriaQuery;
    @Mock
    Root<Guest> root;
    @Mock
    private Predicate name;
    @Mock
    private Predicate status;
    @Mock
    private Predicate gender;
    @Mock
    private Predicate departure;
    @Mock
    private Predicate checkIn;
    @Mock
    private Predicate checkOut;
    @Mock
    private Predicate predicate;
    @Mock
    private LocalDateTime now;
    private final Passport passport = new Passport();
    private final Guest expected = new Guest();
    private final List<Guest> guests = Arrays.asList(new Guest(), new Guest());
    private final String firstname = "Jack";
    private final String lastname = "Sparrow";
    private final Criteria criteria = Criteria
            .builder()
            .name(firstname + " " + lastname)
            .guestStatus(GuestStatus.ARRIVAL)
            .gender(Gender.MAN)
            .departure("2023-12-31")
            .checkIn("2023-01-01")
            .checkOut("2023-12-31").build();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        guestDao = new JpaGuestDao(factory);
    }

    @Test
    @DisplayName("When finding all Guests, Then the method should return a list of all Guests")
    void testFindAll() {
        List<Guest> expectedGuests = Arrays.asList(new Guest(), new Guest());
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Guest.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Guest.class)).thenReturn(root);
        when(root.fetch("passport", JoinType.LEFT)).thenReturn(mock(Fetch.class));
        when(root.fetch("rooms", JoinType.LEFT)).thenReturn(mock(Fetch.class));
        when(entityManager.createQuery(criteriaQuery)).thenReturn(guestQuery);
        when(guestQuery.getResultList()).thenReturn(expectedGuests);
        List<Guest> actualGuests = guestDao.findAll();

        assertEquals(expectedGuests, actualGuests);
        verify(factory).createEntityManager();
        verify(entityManager).getCriteriaBuilder();
        verify(criteriaBuilder).createQuery(Guest.class);
        verify(criteriaQuery).from(Guest.class);
        verify(root).fetch("passport", JoinType.LEFT);
        verify(root).fetch("rooms", JoinType.LEFT);
        verify(entityManager).createQuery(criteriaQuery);
        verify(guestQuery).getResultList();
    }

    @Test
    @DisplayName("Given a Passport, When finding a Guest by Passport data, Then the Guest should be retrieved if it exists")
    void testFindByPassportData() {
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Guest.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Guest.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaBuilder.equal(root.get("passportData"), passport.getGuest())).thenReturn(mock(Predicate.class));
        when(criteriaQuery.where(any(Predicate.class))).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(guestQuery);
        when(guestQuery.getResultList()).thenReturn(Collections.singletonList(expected));
        Optional<Guest> actualGuest = guestDao.findByPassportData(passport);

        assertTrue(actualGuest.isPresent());
        assertEquals(expected, actualGuest.get());
        verify(factory).createEntityManager();
        verify(entityManager).getCriteriaBuilder();
        verify(criteriaBuilder).createQuery(Guest.class);
        verify(criteriaQuery).from(Guest.class);
        verify(criteriaQuery).select(root);
        verify(criteriaBuilder).equal(root.get("passportData"), passport.getGuest());
        verify(criteriaQuery).where(any(Predicate.class));
        verify(entityManager).createQuery(criteriaQuery);
        verify(guestQuery).getResultList();
    }

    @Test
    @DisplayName("Given search criteria, When finding Guests by criteria, Then a list of matching Guests should be returned")
    void testFindByCriteria() {
        String start = " 00:00:00";
        String end = " 23:59:59";

        List<Guest> expectedGuests = Arrays.asList(new Guest(), new Guest());
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Guest.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Guest.class)).thenReturn(root);
        when(criteriaBuilder.like(criteriaBuilder.lower(root.get("passportData")), "%" + criteria.getName()
                                                                                                 .toLowerCase() + "%")).thenReturn(name);
        when(criteriaBuilder.equal(root.get("guestStatus"), criteria.getGuestStatus())).thenReturn(status);
        when(criteriaBuilder.equal(root.get("gender"), criteria.getGender())).thenReturn(gender);
        when(criteriaBuilder.lessThanOrEqualTo(root.get("departure"), Timestamp.valueOf(criteria.getDeparture() + end))).thenReturn(departure);

        if (criteria.getCheckIn() != null && criteria.getCheckOut() != null) {
            Predicate checkInOutPredicate = mock(Predicate.class);
            when(criteriaBuilder.between(root.get("checkIn"), Timestamp.valueOf(criteria.getCheckIn() + start),
                    Timestamp.valueOf(criteria.getCheckOut() + end))).thenReturn(checkInOutPredicate);
            when(criteriaQuery.where(name, status, gender, departure, checkInOutPredicate)).thenReturn(criteriaQuery);
        } else {
            if (criteria.getCheckIn() != null) {
                when(criteriaBuilder.greaterThanOrEqualTo(root.get("checkIn"), Timestamp.valueOf(criteria.getCheckIn() + start))).thenReturn(checkIn);
                when(criteriaQuery.where(name, status, gender, departure, checkIn)).thenReturn(criteriaQuery);
            }
            if (criteria.getCheckOut() != null) {
                when(criteriaBuilder.lessThanOrEqualTo(root.get("checkOut"), Timestamp.valueOf(criteria.getCheckOut() + end))).thenReturn(checkOut);
                when(criteriaQuery.where(name, status, gender, departure, checkOut)).thenReturn(criteriaQuery);
            }
        }
        when(entityManager.createQuery(criteriaQuery)).thenReturn(guestQuery);
        when(guestQuery.getResultList()).thenReturn(expectedGuests);
        List<Guest> actualGuests = guestDao.findByCriteria(criteria);

        assertEquals(expectedGuests, actualGuests);
        verify(factory).createEntityManager();
        verify(entityManager).getCriteriaBuilder();
        verify(criteriaBuilder).createQuery(Guest.class);
        verify(criteriaQuery).from(Guest.class);
        verify(criteriaBuilder).conjunction();
    }

    @Test
    @DisplayName("Given criteria with check-in and check-out dates, When searching for guests, Then it should return guests within the date range")
    void testElseScopeFindByCriteria() {
        Criteria criteria = new Criteria();
        criteria.setName("Jack");
        criteria.setGuestStatus(GuestStatus.ARRIVAL);
        criteria.setGender(Gender.MAN);
        criteria.setDeparture(null);
        criteria.setCheckIn("2023-01-01");
        criteria.setCheckOut(null);
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Guest.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Guest.class)).thenReturn(root);
        when(criteriaBuilder.and(predicate, checkIn)).thenReturn(predicate);
        when(criteriaBuilder.and(predicate, checkOut)).thenReturn(predicate);
        when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(guestQuery);
        when(guestQuery.getResultList()).thenReturn(guests);
        List<Guest> actual = guestDao.findByCriteria(criteria);

        assertEquals(guests, actual);
        verify(factory).createEntityManager();
        verify(entityManager).getCriteriaBuilder();
        verify(criteriaBuilder).createQuery(Guest.class);
        verify(criteriaQuery).from(Guest.class);
        verify(criteriaBuilder).conjunction();
    }

    @Test
    @DisplayName("Given First Name and Last Name, When findByFullName, Then return Optional of Guest")
    void testFindByFullName() {
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.createQuery(SELECT_BY_FULL_NAME, Guest.class)).thenReturn(guestQuery);
        when(guestQuery.setParameter("full", firstname + " " + lastname)).thenReturn(guestQuery);
        when(guestQuery.getSingleResult()).thenReturn(expected);
        Optional<Guest> actualGuest = guestDao.findByFullName(firstname, lastname);

        assertTrue(actualGuest.isPresent());
        assertEquals(expected, actualGuest.get());
        verify(factory).createEntityManager();
        verify(entityManager).createQuery(SELECT_BY_FULL_NAME, Guest.class);
        verify(guestQuery).setParameter("full", firstname + " " + lastname);
        verify(guestQuery).getSingleResult();
    }

    @Test
    @DisplayName("Given a Guest with valid data, When updating the Guest, Then the update should be successful")
    void testUpdate() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.merge(expected)).thenReturn(this.expected);
        Guest actualGuest = guestDao.update(expected);

        assertEquals(this.expected, actualGuest);
        verify(factory).createEntityManager();
        verify(entityManager).getTransaction();
        verify(transaction).begin();
        verify(entityManager).merge(expected);
        verify(transaction).commit();
    }

    @Test
    @DisplayName("Given a departure date, When finding Guests by departure date, Then a list of Guests departing on that date should be returned")
    void testFindByDepartureDate() {
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Guest.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Guest.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaBuilder.lessThanOrEqualTo(root.get("checkOut"), now)).thenReturn(predicate);
        when(criteriaQuery.where(predicate)).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(guestQuery);
        when(guestQuery.getResultList()).thenReturn(guests);
        List<Guest> actualGuests = guestDao.findByDepartureDate(now);

        assertEquals(guests, actualGuests);
        verify(factory).createEntityManager();
        verify(entityManager).getCriteriaBuilder();
        verify(criteriaBuilder).createQuery(Guest.class);
        verify(criteriaQuery).from(Guest.class);
        verify(criteriaQuery).select(root);
        verify(criteriaBuilder).lessThanOrEqualTo(root.get("checkOut"), now);
        verify(criteriaQuery).where(predicate);
        verify(entityManager).createQuery(criteriaQuery);
        verify(guestQuery).getResultList();
    }

    @Test
    @DisplayName("Given a Guest with invalid data, When attempting to update the Guest, Then the update should be rolled back")
    void testUpdateRollback() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.merge(expected)).thenThrow(new PersistenceException("Error updating Guest"));
        when(transaction.isActive()).thenReturn(true);

        assertThrows(PersistenceException.class, () -> guestDao.update(expected));
        verify(factory).createEntityManager();
        verify(entityManager).getTransaction();
        verify(transaction).begin();
        verify(entityManager).merge(expected);
        verify(transaction, never()).commit();
        verify(transaction).rollback();
    }
}
