package com.manager.hotel.service;

import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Room;

import java.util.List;

public interface RoomService {
    List<RoomDto> findRooms();

    Room findAvailableRoom(Criteria criteria);

    Room findRoomById(Long id);
}
