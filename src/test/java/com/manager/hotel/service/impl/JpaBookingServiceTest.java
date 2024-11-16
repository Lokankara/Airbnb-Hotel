package com.manager.hotel.service.impl;

import com.manager.hotel.dao.BookingDao;
import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.service.mapper.BookingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.manager.hotel.web.MockData.roomId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class JpaBookingServiceTest {

    @Mock
    private BookingDao dao;

    @Mock
    private BookingMapper mapper;

    @InjectMocks
    private JpaBookingService bookingService;

    private Booking booking;
    private Booking booking2;
    private BookingDto bookingDto;
    private BookingDto bookingDto2;
    private final List<Booking> bookings = new ArrayList<>();
    private final List<BookingDto> bookingDtos = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        booking = Booking.builder().id(1L).build();
        booking2 = Booking.builder().id(2L).build();
        bookingDto = BookingDto.builder().id(1L).build();
        bookingDto2 = BookingDto.builder().id(2L).build();
        bookings.add(booking);
        bookings.add(booking2);
        bookingDtos.add(bookingDto);
        bookingDtos.add(bookingDto2);
    }

    @Test
    @DisplayName("Given bookings in the database, when findAll is called, then return a list of booking DTOs")
    void testFindAllWithBookingsInDatabase() {
        when(dao.findAll()).thenReturn(bookings);
        when(mapper.toListDto(bookings)).thenReturn(bookingDtos);
        List<BookingDto> resultBookingDtos = bookingService.findAll();
        assertNotNull(resultBookingDtos);
        assertEquals(2, resultBookingDtos.size());
        assertEquals(bookingDto.getId(), resultBookingDtos.get(0).getId());
        assertEquals(bookingDto2.getId(), resultBookingDtos.get(1).getId());
    }

    @Test
    @DisplayName("Given no bookings in the database, when findAll is called, then return an empty list")
    void testFindAllWithNoBookingsInDatabase() {
        when(dao.findAll()).thenReturn(new ArrayList<>());
        List<BookingDto> resultBookingDtos = bookingService.findAll();
        assertNotNull(resultBookingDtos);
        assertTrue(resultBookingDtos.isEmpty());
    }

    @Test
    @DisplayName("Given a timestamp, when getLatest is called, then return a list of latest booking DTOs")
    void testGetLatestBookings() {
        Timestamp fromDate = Timestamp.valueOf("2023-08-01 00:00:00");
        when(mapper.toListDto(bookings)).thenReturn(bookingDtos);
        List<BookingDto> resultBookingDtos = bookingService.findLatest();
        assertNotNull(resultBookingDtos);
        assertEquals(2, resultBookingDtos.size());
        assertEquals(bookingDto.getId(), resultBookingDtos.get(0).getId());
        assertEquals(bookingDto2.getId(), resultBookingDtos.get(1).getId());
    }

    @Test
    @DisplayName("Given a room ID, when findByRoomId is called, then return a BookingDto object")
    void testFindByRoomId() {
        BookingDto bookingDto = new BookingDto();
        when(bookingService.findByRoomId(roomId)).thenReturn(bookingDto);
        BookingDto result = bookingService.findByRoomId(roomId);
        assertEquals(bookingDto, result);
    }

    @Test
    @DisplayName("Given a booking, when save is called, then return a booking DTO")
    void testSaveBooking() {
        when(dao.save(booking)).thenReturn(booking);
        Booking savedBookingDto = bookingService.save(booking);
        assertNotNull(savedBookingDto);
        assertEquals(bookingDto.getId(), savedBookingDto.getId());
    }
}
