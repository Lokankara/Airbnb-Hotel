package com.manager.hotel.service;

import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;

import java.util.List;
import java.util.Optional;

public interface GuestService {
    List<GuestDto> getAllGuests();

    List<GuestDto> findDepartingToday();

    Guest findGuestById(Long id);

    List<GuestDto> findByCriteria(Criteria criteria);

    GuestDto update(GuestDto guest);

    Guest save(Guest guest);

    Optional<Guest> findByPassport(Passport passport);

    Guest update(Guest guest);
}
