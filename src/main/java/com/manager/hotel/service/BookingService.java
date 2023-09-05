package com.manager.hotel.service;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.entity.Booking;

import java.util.List;

public interface BookingService {
    List<BookingDto> findAll();

    List<BookingDto> findLatest();

    Booking findByRoomId(Long id);

    BookingDto save(Booking booking);
}
