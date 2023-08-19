package com.manager.hotel.service.impl;

import com.manager.hotel.dao.GuestDao;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.service.GuestService;
import com.manager.hotel.service.mapper.GuestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.time.LocalDateTime.now;

@Service
@Transactional
@RequiredArgsConstructor
public class JpaGuestService implements GuestService {

    private final GuestMapper mapper;
    private final GuestDao guestRepository;

    @Override
    public List<GuestDto> getAllGuests() {
        return mapper.toListDto(guestRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestDto> searchGuestsByPassportData(
            final String passportData) {
        return mapper.toListDto(guestRepository
                .findByPassportData(passportData));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GuestDto checkInGuest(
            final Guest guest,
            final Room room) {
        guest.setArrivalDate(now());
        guest.setRoom(room);
        guestRepository.save(guest);
        return mapper.toDto(guest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestDto> findGuestsByCharacteristic(
            final String characteristic) {
        return mapper.toListDto(guestRepository
                .findByPassportData(characteristic));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestDto> findGuestsDepartingToday() {
        return mapper.toListDto(guestRepository
                .findByDepartureDate(now()));
    }

    @Override
    @Transactional(readOnly = true)
    public Guest findGuestById(Long id) {
        return guestRepository.getById(id);
    }
}
