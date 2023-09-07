package com.manager.hotel.service.impl;

import com.manager.hotel.dao.jpa.JpaGuestDao;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;
import com.manager.hotel.service.GuestService;
import com.manager.hotel.service.mapper.GuestMapper;
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
    private final JpaGuestDao dao;

    @Override
    public List<GuestDto> getAllGuests() {
        return mapper.toListDto(
                dao.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuestDto> findByCriteria(
            final Criteria criteria) {
        return mapper.toListDto(dao
                .findByCriteria(criteria));
    }

    @Override
    public GuestDto update(GuestDto dto) {
        return mapper.toDto(update(
                mapper.toEntity(dto)));
    }
    @Transactional(rollbackFor = Exception.class)
    public Guest update(Guest guest) {
        return dao.update(guest);
    }

    @Override
    public Optional<Guest> findByFullName(String firstname, String lastname) {
        return dao.findByFullName(firstname, lastname);
    }

    @Override
    public Guest save(final Guest guest) {
        return dao.save(guest);
    }

    @Override
    public Optional<Guest> findByPassport(
            Passport passport) {
        return dao.findByPassportData(passport);
    }

    @Override
    @Transactional(readOnly = true)
    public Guest findGuestById(
            final Long id) {
        return dao.getById(id);
    }
}
