package com.manager.hotel.service.impl;

import com.manager.hotel.dao.jpa.JpaGuestDao;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
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
    private final JpaGuestDao guestRepository;

    @Override
    public List<GuestDto> getAllGuests() {
        return mapper.toListDto(
                guestRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestDto> findByCriteria(
            final Criteria criteria) {
        return mapper.toListDto(guestRepository
                .findByCriteria(criteria));
    }

    @Override
    public GuestDto delete(String id) {
        return null; //TODO
    }

    @Override
    public GuestDto updateStatus(final String id) {
        return null; //TODO
    }

    @Override
    public Guest save(final Guest guest) {
        return guestRepository.save(guest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestDto> findDepartingToday() {
        return mapper.toListDto(guestRepository
                .findByDepartureDate(now()));
    }

    @Override
    @Transactional(readOnly = true)
    public Guest findGuestById(
            final Long id) {
        return guestRepository
                .getById(id);
    }
}
