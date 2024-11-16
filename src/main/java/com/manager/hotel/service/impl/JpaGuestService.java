package com.manager.hotel.service.impl;

import com.manager.hotel.dao.GuestDao;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;
import com.manager.hotel.service.GuestService;
import com.manager.hotel.service.mapper.GuestMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class JpaGuestService implements GuestService {

    private final GuestMapper mapper;
    private final GuestDao dao;

    @Override
    public List<GuestDto> getAllGuests() {
        return mapper.toListDto(dao.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestDto> findByCriteria(final Criteria criteria) {
        return mapper.toListDto(dao.findAll());
    }

    @Override
    public GuestDto update(GuestDto dto) {
        return mapper.toDto(update(mapper.toEntity(dto)));
    }

    @Transactional(rollbackFor = Exception.class)
    public Guest update(Guest guest) {
        return dao.save(guest);
    }

    @Override
    public Optional<Guest> findByFullName(String firstname, String lastname) {
        Passport passport = new Passport();
        passport.setFirstname(firstname);
        passport.setLastname(lastname);
        return dao.findByPassport(passport);
    }

    @Override
    public Guest save(final Guest guest) {
        return dao.save(guest);
    }

    @Override
    public Optional<Guest> findByPassport(Passport passport) {
        return dao.findById(passport.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Guest findGuestById(final Long id) {
        return dao.findById(id).orElseThrow(() -> new EntityNotFoundException("Guest not found"));
    }
}
