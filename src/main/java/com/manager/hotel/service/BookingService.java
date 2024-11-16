package com.manager.hotel.service;

import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.model.dto.BookingDto;

import java.util.List;

public interface BookingService {
    List<BookingDto> findAll();

    List<BookingDto> findLatest();

    Booking save(Booking booking);

    BookingDto toDto(Booking booking);

    BookingDto update(Booking booking);

    BookingDto findByRoomId(Long roomId);
}
