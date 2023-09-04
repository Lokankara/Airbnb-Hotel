package com.manager.hotel.service.impl;

import com.manager.hotel.dao.jpa.JpaBookingDao;
import com.manager.hotel.dao.jpa.JpaGuestDao;
import com.manager.hotel.exception.GuestNotFoundException;
import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.service.BookingService;
import com.manager.hotel.service.mapper.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.Duration.between;

@Service
@Transactional
@RequiredArgsConstructor
public class JpaBookingService implements BookingService {
    private final JpaGuestDao jpaGuestDao;
    private final JpaBookingDao dao;
    private final BookingMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookingDto checkOutGuest(
            final Long guestId,
            final boolean earlyDeparture) {
        Guest guest = jpaGuestDao
                .findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException(
                        "Guest not found with ID " + guestId));

        Booking checkOut = getCheckOut(earlyDeparture, guest);
        guest.getRooms().forEach(room -> room.setRoomStatus(RoomStatus.VACANT));
        jpaGuestDao.save(guest);
        return mapper.toDto(dao
                .save(checkOut));
    }

    @Override
    public List<BookingDto> findAll() {
        return mapper.toListDto(
                dao.findAll());
    }

    @Override
    public List<BookingDto> getLatest(
            Timestamp fromDate) {
        return mapper.toListDto(dao
                .findLatestDeals(fromDate));
    }


    @Override
    public BookingDto save(
            Booking booking) {
        return mapper.toDto(
                dao.save(booking));
    }

    private static Booking getCheckOut(
            final boolean earlyDeparture,
            final Guest guest) {
        long nights = getNights(earlyDeparture, guest);
        return Booking
                .builder()
                .guest(guest)
                .earlyDeparture(earlyDeparture)
                .finalBill(nights * guest.getRate())
                .build();
    }

    private static long getNights(
            final boolean earlyDeparture,
            final Guest guest) {
        LocalDateTime in = guest.getArrivalDate().toLocalDateTime();
        LocalDateTime out = guest.getDepartureDate().toLocalDateTime();
        return earlyDeparture ?
                between(in, out).toDays() :
                between(in, out.plusDays(1)).toDays();
    }
}
