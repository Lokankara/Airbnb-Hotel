package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Booking;

public abstract class BookingDao extends Dao<Booking> {
    protected BookingDao() {
        super(Booking.class);
    }
}
