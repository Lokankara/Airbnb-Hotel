package com.manager.hotel.dao.jpa;

import com.manager.hotel.model.entity.Passport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static com.manager.hotel.dao.jpa.Constant.SELECT_PASSPORT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JpaPassportDaoTest {

    private JpaPassportDao passportDao;
    @Mock
    private EntityManagerFactory factory;
    @Mock
    private EntityManager entityManager;
    @Mock
    TypedQuery<Passport> query;
    Passport expectedPassport = new Passport();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        passportDao = new JpaPassportDao(factory);
    }

    @Test
    @DisplayName("Given a first name and last name, When searching for a Passport by first name and last name, Then the Passport should be found if it exists")
    void testFindByFirstNameAndLastName() {
        String firstname = "Jack";
        String lastname = "Sparrow";
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.createQuery(SELECT_PASSPORT, Passport.class)).thenReturn(query);
        when(query.setParameter("firstname", firstname)).thenReturn(query);
        when(query.setParameter("lastname", lastname)).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(expectedPassport));

        Optional<Passport> actualPassport = passportDao.findByFirstNameAndLastName(firstname, lastname);
        assertTrue(actualPassport.isPresent());
        assertEquals(expectedPassport, actualPassport.get());
        verify(factory).createEntityManager();
        verify(entityManager).createQuery(SELECT_PASSPORT, Passport.class);
        verify(query).setParameter("firstname", firstname);
        verify(query).setParameter("lastname", lastname);
        verify(query).getResultList();
    }
}
