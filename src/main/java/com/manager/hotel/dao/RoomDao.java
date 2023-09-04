package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Room;

import java.util.List;

public abstract class RoomDao extends Dao<Room> {

    protected RoomDao() {
        super(Room.class);
    }

    public abstract Room getById(Long id);

    public abstract List<Room> findByCriteria(Criteria criteria);

}
