package com.manager.hotel.service.facade;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.service.BookingService;
import com.manager.hotel.service.GuestService;
import com.manager.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelFacade {
    private final RoomService roomService;
    private final GuestService guestService;
    private final BookingService bookingService;

    public List<GuestDto> getAllGuests() {
        return guestService.getAllGuests();
    }

    public List<RoomDto> getAllRooms() {
        return roomService.findRooms();
    }

    public BookingDto checkInGuest(Long guestId, Long roomId) {
        Guest guest = guestService.findGuestById(guestId);
        Room room = roomService.findRoomById(roomId);
        return bookingService.checkInGuest(guest, room);
    }

    public BookingDto checkOutGuest(
            Long guestId, boolean earlyDeparture) {
        return bookingService.checkOutGuest(
                guestId, earlyDeparture);
    }

    public List<BookingDto> getAllBooking() {
        return bookingService.findAll();
    }
}
