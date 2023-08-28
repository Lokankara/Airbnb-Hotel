package com.manager.hotel.service.impl;

import com.manager.hotel.dao.PassportDao;
import com.manager.hotel.model.entity.Passport;
import com.manager.hotel.service.PassportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class JpaPassportService implements PassportService {

    private final PassportDao passportDao;

    @Override
    public Passport save(
            final Passport passport) {
        return passportDao.save(passport);
    }

    @Override
    public Optional<Passport> findByFirstNameAndLastName(
            final String name, final String surname) {
        return passportDao
                .findByFirstNameAndLastName(name, surname);
    }
}
