package com.manager.hotel.service;

import com.manager.hotel.model.dto.CheckOutDto;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.service.impl.JpaCheckOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelFacade {
    private final RoomService roomService;
    private final GuestService guestService;
    private final JpaCheckOutService checkOutService;

    public List<GuestDto> getAllGuests() {
        return guestService.getAllGuests();
    }

    public List<GuestDto> searchGuestsByPassportData(
            final String passportData) {
        return guestService
                .searchGuestsByPassportData(passportData);
    }

    public GuestDto checkInGuest(
            final Long guestId,
            final Long roomId) {
        Guest guest = guestService.findGuestById(guestId);
        Room room = roomService.findRoomById(roomId);
        return guestService.checkInGuest(guest, room);
    }

    public CheckOutDto checkOutGuest(
            final Long guestId,
            final boolean earlyDeparture) {
        return checkOutService
                .checkOutGuest(guestId, earlyDeparture);
    }

    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms();
    }

    public List<GuestDto> findGuests(String characteristic) {
        return guestService.findGuestsByCharacteristic(characteristic);
    }

    public List<GuestDto> findDepartingToday() {
        return guestService.findGuestsDepartingToday();
    }
}
