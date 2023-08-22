package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;

import java.time.LocalDateTime;
import java.util.List;

public abstract class GuestDao extends Dao<Guest> {
    protected GuestDao() {
        super(Guest.class);
    }

    public abstract Guest findByPassportData(Passport passport);

    public abstract List<Guest> findByDepartureDate(LocalDateTime now);

    public abstract Guest getById(Long id);

    public abstract List<Guest> findByCriteria(Criteria criteria);
}
