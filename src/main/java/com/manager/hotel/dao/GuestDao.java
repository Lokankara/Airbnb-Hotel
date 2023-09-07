package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class GuestDao extends Dao<Guest> {
    protected GuestDao() {
        super(Guest.class);
    }

    public abstract Optional<Guest> findByPassportData(Passport passport);

    public abstract List<Guest> findByDepartureDate(LocalDateTime now);

    public abstract Guest getById(Long id);

    public abstract List<Guest> findByCriteria(Criteria criteria);

    public abstract Guest update(Guest guest);

    public abstract Optional<Guest> findByFullName(String firstname, String lastname);
}
