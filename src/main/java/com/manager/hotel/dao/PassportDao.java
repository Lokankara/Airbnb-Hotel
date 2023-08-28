package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Passport;

import java.util.Optional;

public abstract class PassportDao extends Dao<Passport> {

    protected PassportDao() {
        super(Passport.class);
    }

    public abstract Optional<Passport> findByFirstNameAndLastName(
            String name, String surname);
}
