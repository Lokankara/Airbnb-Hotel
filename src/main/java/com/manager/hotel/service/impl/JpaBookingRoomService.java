package com.manager.hotel.service.impl;

import com.manager.hotel.dao.BookingDao;
import com.manager.hotel.model.dto.BookedDateDTO;
import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.service.BookingRoomService;
import com.manager.hotel.mapper.BookingRoomMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class JpaBookingRoomService implements BookingRoomService {

    private final BookingDao dao;
    private final BookingRoomMapper mapper;

    @Override
    public List<BookingDto> findAll() {
        return mapper.toListDto(
                dao.findAll());
    }

    @Override
    public List<BookingDto> findLatest() {
        return mapper.toListDto(dao.findAll());
    }

    @Override
    public Booking save(Booking booking) {
        return dao.save(booking);
    }

    @Override
    public BookingDto toDto(Booking booking) {
        return mapper.toDto(booking);
    }

    @Override
    public BookingDto update(Booking booking) {
        return mapper.toDto(dao.save(booking));
    }

    @Override
    public BookingDto findByRoomId(Long roomId) {
        return mapper.toDto(
                dao.findById(roomId).orElse(new Booking()));
    }

    @Override
    public List<UUID> getBookingMatchByListingIdsAndBookedDate(List<UUID> uuiDs, BookedDateDTO dates) {
        return List.of();
    }
}
