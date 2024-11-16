package com.manager.hotel.service;


import com.manager.hotel.model.entity.Passport;

import java.util.Optional;

public interface PassportService {
    Passport save(Passport passport);

    Optional<Passport> findByFirstNameAndLastName(String name, String surname);
}
