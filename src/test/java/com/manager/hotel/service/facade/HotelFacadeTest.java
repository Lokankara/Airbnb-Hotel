package com.manager.hotel.service.facade;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.model.entity.Passport;
import com.manager.hotel.model.entity.Room;
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
import java.util.Optional;

import static com.manager.hotel.service.facade.HotelFacade.getCriteria;
import static com.manager.hotel.web.MockData.booking;
import static com.manager.hotel.web.MockData.criteria;
import static com.manager.hotel.web.MockData.firstname;
import static com.manager.hotel.web.MockData.jack;
import static com.manager.hotel.web.MockData.lastname;
import static com.manager.hotel.web.MockData.passport;
import static com.manager.hotel.web.MockData.postDto;
import static com.manager.hotel.web.MockData.room;
import static com.manager.hotel.web.MockData.roomId;
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
        when(roomService.findAll()).thenReturn(expectedRooms);

        List<RoomDto> actualRooms = hotelFacade.getAllRooms();

        assertEquals(expectedRooms, actualRooms);
        verify(roomService, times(1)).findAll();
    }

    @Test
    @DisplayName("Given a Criteria object, When finding available rooms, Then a list of available RoomDto objects should be returned")
    void testFindAvailableRooms() {
        List<RoomDto> expectedRooms = new ArrayList<>();
        when(roomService.findAvailableRooms(criteria)).thenReturn(expectedRooms);
        List<RoomDto> actualRooms = hotelFacade.findAvailableRooms(criteria);
        assertEquals(expectedRooms, actualRooms);
        verify(roomService, times(1)).findAvailableRooms(criteria);
    }

    @Test
    void testFindById() {
        Room expectedRoom = new Room();
        expectedRoom.setId(roomId);
        Optional<Room> expectedOptionalRoom = Optional.of(expectedRoom);
        when(roomService.findById(roomId)).thenReturn(expectedOptionalRoom);
        Optional<Room> actualOptionalRoom = roomService.findById(roomId);
        assertEquals(expectedOptionalRoom, actualOptionalRoom);
    }

    @Test
    @DisplayName("Given a firstname and lastname, when findByFullName is called, then return guest")
    void testFindByFirstNameAndLastName() {
        when(passportService.findByFirstNameAndLastName(firstname, lastname)).thenReturn(Optional.of(passport));
        Optional<Passport> result = passportService.findByFirstNameAndLastName(firstname, lastname);
        assertEquals(Optional.of(passport), result);
    }

    @Test
    void testUpdate() {
        BookingDto updatedBookingDto = new BookingDto();
        when(bookingService.update(booking)).thenReturn(updatedBookingDto);
        BookingDto result = bookingService.update(booking);
        assertEquals(updatedBookingDto, result);
    }

    @Test
    void testSaveBookingWithDate() {
        Booking booking = new Booking();
        booking.setNights(4L);
        booking.setRate(100L);
        booking.setGuest(jack);
        booking.setRoom(room);
        when(roomService.findAvailable(getCriteria(postDto))).thenReturn(Optional.of(room));
        when(guestService.findByFullName(postDto.getFirstname(), postDto.getLastname())).thenReturn(Optional.of(jack));
        when(passportService.findByFirstNameAndLastName(postDto.getFirstname(), postDto.getLastname())).thenReturn(Optional.of(passport));
        when(bookingService.save(booking)).thenReturn(booking);
        Booking result = bookingService.save(booking);
        assertEquals(booking, result);
    }

    @Test
    void testSaveBooking() {
        when(roomService.findAvailable(getCriteria(postDto))).thenReturn(Optional.of(room));
        when(guestService.findByFullName(postDto.getFirstname(), postDto.getLastname())).thenReturn(Optional.of(jack));
        when(passportService.findByFirstNameAndLastName(postDto.getFirstname(), postDto.getLastname())).thenReturn(Optional.of(passport));
        when(bookingService.save(booking)).thenReturn(booking);
        Booking result = bookingService.save(booking);
        assertEquals(booking, result);
    }
}

