package com.manager.hotel.service;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.entity.Booking;

import java.sql.Timestamp;
import java.util.List;

public interface BookingService {

    BookingDto checkOutGuest(Long guestId, boolean earlyDeparture);

    List<BookingDto> findAll();

    List<BookingDto> getLatest(Timestamp date);

    BookingDto save(Booking booking);
}
