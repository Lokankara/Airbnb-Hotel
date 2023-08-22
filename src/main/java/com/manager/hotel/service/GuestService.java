package com.manager.hotel.service;

import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GuestService {
    List<GuestDto> getAllGuests();

    List<GuestDto> findDepartingToday();

    Guest findGuestById(Long id);

    List<GuestDto> findByCriteria(Criteria criteria);
}
