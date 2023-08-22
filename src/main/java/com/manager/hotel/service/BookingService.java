package com.manager.hotel.service;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Room;

import java.util.List;

public interface BookingService {
    BookingDto checkInGuest(Guest guest, Room room);

    BookingDto checkOutGuest(Long guestId, boolean earlyDeparture);

    List<BookingDto> findAll();
}
