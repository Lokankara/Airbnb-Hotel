package com.manager.hotel.service.impl;

import com.manager.hotel.dao.BookingDao;
import com.manager.hotel.dao.GuestDao;
import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.exception.GuestNotFoundException;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.service.BookingService;
import com.manager.hotel.service.mapper.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.LocalDateTime.now;

@Service
@Transactional
@RequiredArgsConstructor
public class JpaBookingService implements BookingService {
    private final GuestDao guestDao;
    private final BookingDao bookingDao;
    private final BookingMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookingDto checkInGuest(
            final Guest guest,
            final Room room) {
        guest.setArrivalDate(now());
        guest.setRoom(room);
        Guest saved = guestDao.save(guest);
        Booking booking = Booking
                .builder()
                .guest(guest)
                .guest(saved)
                .build();
        return mapper.toDto(booking);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookingDto checkOutGuest(
            final Long guestId,
            final boolean earlyDeparture) {
        Guest guest = guestDao.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException(
                        "Guest not found with ID " + guestId));

        Booking checkOut = getCheckOut(earlyDeparture, guest);
        Booking saved = bookingDao.save(checkOut);
        guest.setRoom(null);
        guestDao.save(guest);
        return mapper.toDto(saved);
    }

    private static Booking getCheckOut(
            boolean earlyDeparture,
            Guest guest) {
        long nights = getNights(earlyDeparture, guest);
        return Booking
                .builder()
                .guest(guest)
                .earlyDeparture(earlyDeparture)
                .finalBill(nights * guest.getRate())
                .build();
    }

    private static long getNights(
            boolean earlyDeparture,
            Guest guest) {
        long nights = ChronoUnit.DAYS.between(
                guest.getArrivalDate(),
                LocalDate.now());
        if (earlyDeparture) {
            nights--;
        }
        return nights;
    }
}
