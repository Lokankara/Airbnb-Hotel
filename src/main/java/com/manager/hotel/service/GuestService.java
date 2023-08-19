package com.manager.hotel.service;

import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.entity.Guest;

import java.util.List;

public interface GuestService {
    List<GuestDto> getAllGuests();

    List<GuestDto> searchGuestsByPassportData(
            String passportData);

    List<GuestDto> findGuestsByCharacteristic(
            String characteristic);

    List<GuestDto> findGuestsDepartingToday();

    Guest findGuestById(Long id);
}
