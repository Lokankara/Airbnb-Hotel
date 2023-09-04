package com.manager.hotel.service.facade;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.service.BookingService;
import com.manager.hotel.service.GuestService;
import com.manager.hotel.service.PassportService;
import com.manager.hotel.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HotelFacadeTest {

    @InjectMocks
    private HotelFacade hotelFacade;

    @Mock
    private RoomService roomService;

    @Mock
    private GuestService guestService;

    @Mock
    private BookingService bookingService;

    @Mock
    private PassportService passportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given guest service returns guests, When getting all guests, Then return the expected guests")
    void testGetAllGuests() {
        List<GuestDto> expectedGuests = new ArrayList<>();
        when(guestService.getAllGuests()).thenReturn(expectedGuests);

        List<GuestDto> actualGuests = hotelFacade.getAllGuests();

        assertEquals(expectedGuests, actualGuests);
        verify(guestService, times(1)).getAllGuests();
    }

    @Test
    @DisplayName("Given room service returns rooms, When getting all rooms, Then return the expected rooms")
    void testGetAllRooms() {
        List<RoomDto> expectedRooms = new ArrayList<>();
        when(roomService.findRooms()).thenReturn(expectedRooms);

        List<RoomDto> actualRooms = hotelFacade.getAllRooms();

        assertEquals(expectedRooms, actualRooms);
        verify(roomService, times(1)).findRooms();
    }

    @Test

    @DisplayName("Given guest ID and early departure flag, When checking out a guest, Then return the booking details")
    void testCheckOutGuest() {
        Long guestId = 1L;
        boolean earlyDeparture = true;
        BookingDto expectedBookingDto = new BookingDto();
        when(bookingService.checkOutGuest(guestId, earlyDeparture)).thenReturn(expectedBookingDto);
        BookingDto actualBookingDto = hotelFacade.checkOutGuest(guestId, earlyDeparture);
        assertEquals(expectedBookingDto, actualBookingDto);
        verify(bookingService, times(1)).checkOutGuest(guestId, earlyDeparture);
    }

    @Test
    @DisplayName("Given valid criteria, when findAvailableRooms is called, then return a list of rooms")
    void testFindAvailableRooms() {
        Criteria criteria = new Criteria();
        List<RoomDto> expectedRooms = new ArrayList<>();
        when(roomService.findAvailableRooms(criteria)).thenReturn(expectedRooms);
        List<RoomDto> actualRooms = hotelFacade.findAvailableRooms(criteria);
        assertEquals(expectedRooms, actualRooms);
        verify(roomService, times(1)).findAvailableRooms(criteria);
    }
}
