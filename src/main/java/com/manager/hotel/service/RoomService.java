package com.manager.hotel.service;

import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<RoomDto> findRooms();

    Optional<Room> findRoomById(Long id);

    List<RoomDto> findAvailableRooms(Criteria criteria);

    Optional<Room> findAvailable(Criteria criteria);

    RoomDto getRoomById(Long id);

    Room update(Room room);
}
