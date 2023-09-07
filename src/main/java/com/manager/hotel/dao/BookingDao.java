package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Booking;

import java.util.List;
import java.util.Optional;

public abstract class BookingDao extends Dao<Booking> {
    protected BookingDao() {
        super(Booking.class);
    }

    public abstract Booking getBookingByRoomId(Long id);

    public abstract List<Booking> findLatestDeals();

    public abstract Optional<Booking> update(Booking booking);
}
