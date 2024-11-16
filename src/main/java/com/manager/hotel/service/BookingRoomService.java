package com.manager.hotel.service;

import com.manager.hotel.model.dto.BookedDateDTO;
import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.model.dto.BookingDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface BookingRoomService {
    List<BookingDto> findAll();

    List<BookingDto> findLatest();

    Booking save(Booking booking);

    BookingDto toDto(Booking booking);

    BookingDto update(Booking booking);

    BookingDto findByRoomId(Long roomId);

    List<UUID> getBookingMatchByListingIdsAndBookedDate(List<UUID> uuiDs, @Valid BookedDateDTO dates);
}
