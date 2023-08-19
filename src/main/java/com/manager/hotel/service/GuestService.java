package com.manager.hotel.service;

import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Room;

import java.util.List;

public interface GuestService {
    List<GuestDto> getAllGuests();

    List<GuestDto> searchGuestsByPassportData(
            String passportData);

    GuestDto checkInGuest(Guest guest, Room room);

    List<GuestDto> findGuestsByCharacteristic(
            String characteristic);

    List<GuestDto> findGuestsDepartingToday();

    Guest findGuestById(Long id);
}
