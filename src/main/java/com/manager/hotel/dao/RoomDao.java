package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Room;

import java.util.List;
import java.util.Optional;

public abstract class RoomDao extends Dao<Room> {

    protected RoomDao() {
        super(Room.class);
    }

    public abstract List<Room> findByCriteria(Criteria criteria);

    public abstract Optional<Room> update(Room room);
}
