package com.manager.hotel.service;

import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.model.entity.RoomType;

import java.util.List;

public interface RoomService {
    List<RoomDto> getAllRooms();

    RoomDto findAvailableRoom(
            RoomType roomType,
            int capacity);

    Room findRoomById(Long id);
}
