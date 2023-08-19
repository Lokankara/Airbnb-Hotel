package com.manager.hotel.service.impl;

import com.manager.hotel.dao.CheckOutDao;
import com.manager.hotel.dao.GuestDao;
import com.manager.hotel.model.dto.CheckOutDto;
import com.manager.hotel.model.entity.CheckOut;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.exception.GuestNotFoundException;
import com.manager.hotel.service.CheckOutService;
import com.manager.hotel.service.mapper.CheckOutMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class JpaCheckOutService implements CheckOutService {
    private final GuestDao guestRepository;
    private final CheckOutDao checkOutRepository;
    private final CheckOutMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CheckOutDto checkOutGuest(
            final Long guestId,
            final boolean earlyDeparture) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestNotFoundException(
                        "Guest not found with ID " + guestId));

        CheckOut checkOut = getCheckOut(earlyDeparture, guest);

        CheckOut saved = checkOutRepository.save(checkOut);
        guest.setRoom(null);
        guestRepository.save(guest);
        return mapper.toDto(saved);
    }

    private static CheckOut getCheckOut(
            boolean earlyDeparture,
            Guest guest) {
        long nights = getNights(earlyDeparture, guest);
        return CheckOut
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
