package com.manager.hotel.service.impl;

import com.manager.hotel.dao.jpa.JpaGuestDao;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.model.enums.RoomType;
import com.manager.hotel.service.mapper.GuestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.manager.hotel.web.MockData.firstname;
import static com.manager.hotel.web.MockData.lastname;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class JpaGuestServiceTest {

    @Mock
    private JpaGuestDao dao;

    @Mock
    private GuestMapper mapper;

    @InjectMocks
    private JpaGuestService guestService;

    private Guest guest;
    private Passport passport;
    private Criteria criteria;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passport = Passport.builder().creditCard("ABC123").build();
        guest =  Guest.builder().id(1L).passport(passport).build();
        criteria = Criteria
                .builder()
                .capacity(2)
                .roomType(RoomType.SINGLE)
                .roomStatus(RoomStatus.VACANT)
                .build();
    }

    @Test
    @DisplayName("Given no guests in the repository, when getAllGuests is called, then return an empty list")
    void testGetAllGuestsWhenNoGuestsInRepository() {
        JpaGuestDao guestRepository = mock(JpaGuestDao.class);
        when(guestRepository.findAll()).thenReturn(Collections.emptyList());
        List<GuestDto> guestDtos = guestService.getAllGuests();
        assertNotNull(guestDtos);
        assertTrue(guestDtos.isEmpty());
    }

    @Test
    @DisplayName("Given valid criteria, when findByCriteria is called, then return a list of GuestDto")
    void testFindByCriteriaWithValidCriteria() {

        when(dao.findByCriteria(criteria)).thenReturn(Collections.singletonList(new Guest()));
        when(mapper.toListDto(anyList())).thenReturn(Collections.singletonList(new GuestDto()));
        List<GuestDto> guestDtos = guestService.findByCriteria(criteria);
        assertNotNull(guestDtos);
        assertFalse(guestDtos.isEmpty());
    }

    @Test
    @DisplayName("Given a valid guest DTO, when update is called, then return the updated guest DTO")
    void testUpdateWithValidGuestDto() {
        GuestDto guestDto = GuestDto
                .builder()
                .id(1L)
                .passportData("AB12345")
                .checkIn(Timestamp.valueOf("2023-08-18 12:00:00"))
                .build();

        when(mapper.toEntity(guestDto)).thenReturn(guest);
        when(mapper.toDto(guest)).thenReturn(guestDto);
        when(guestService.update(guestDto)).thenReturn(guestDto);
        GuestDto updatedGuestDto = guestService.update(guestDto);
        assertNotNull(updatedGuestDto);
        assertEquals(guestDto.getId(), updatedGuestDto.getId());
    }

    @Test
    @DisplayName("Given a valid guest entity, when save is called, then return the saved guest entity")
    void testSaveWithValidGuestEntity() {
        when(dao.save(guest)).thenReturn(guest);
        Guest saved = guestService.save(guest);
        verify(dao).save(guest);
        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        verifyNoMoreInteractions(dao);
    }

    @Test
    @DisplayName("Given a valid passport, when findByPassport is called, then return the guest with matching passport")
    void testFindByPassportWithValidPassport() {
        when(dao.findByPassportData(passport)).thenReturn(Optional.of(guest));
        Optional<Guest> optionalGuest = guestService.findByPassport(passport);
        assertEquals(Optional.of(guest), optionalGuest);
    }

    @Test
    @DisplayName("Given an invalid passport, when findByPassport is called, then return an empty optional")
    void testFindByPassportWithInvalidPassport() {
        when(dao.findByPassportData(passport)).thenReturn(Optional.empty());
        Optional<Guest> foundGuest = guestService.findByPassport(passport);
        assertEquals(Optional.empty(), foundGuest);
    }

    @Test
    @DisplayName("Given a valid guest ID, when findGuestById is called, then return the corresponding guest")
    void testFindGuestByIdWithValidId() {
        Long guestId = 1L;
        Guest expectedGuest = new Guest();
        expectedGuest.setId(guestId);
        expectedGuest.setPassportData("John");
        when(dao.getById(guestId)).thenReturn(expectedGuest);
        Guest resultGuest = guestService.findGuestById(guestId);
        assertNotNull(resultGuest);
        assertEquals(expectedGuest.getId(), resultGuest.getId());
        assertEquals(expectedGuest.getPassportData(), resultGuest.getPassportData());
    }

    @Test
    @DisplayName("Given an invalid guest ID, when findGuestById is called, then return null")
    void testFindGuestByIdWithInvalidId() {
        when(dao.getById(-1L)).thenReturn(null);
        Guest resultGuest = guestService.findGuestById(-1L);
        assertNull(resultGuest);
    }

    @Test
    @DisplayName("Given a firstname and lastname, when findByFullName is called, then return guest")
    void testFindByFullName() {
        when(guestService.findByFullName(firstname, lastname)).thenReturn(Optional.of(guest));
        Optional<Guest> result = guestService.findByFullName(firstname, lastname);
        assertEquals(Optional.of(guest), result);
    }
}
