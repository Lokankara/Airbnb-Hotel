package com.manager.hotel.service.impl;

import com.manager.hotel.dao.jpa.JpaBookingDao;
import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.service.BookingService;
import com.manager.hotel.service.mapper.BookingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class JpaBookingService implements BookingService {

    private final JpaBookingDao dao;
    private final BookingMapper mapper;

    @Override
    public Booking findByRoomId(Long id) {
        return dao.getBookingByRoomId(id);
    }

    @Override
    public List<BookingDto> findAll() {
        return mapper.toListDto(
                dao.findAll());
    }

    @Override
    public List<BookingDto> findLatest() {
        return mapper.toListDto(
                dao.findLatestDeals());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookingDto save(
            Booking booking) {
        return mapper.toDto(
                dao.save(booking));
    }
}
