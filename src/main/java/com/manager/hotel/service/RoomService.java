package com.manager.hotel.service;

import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.model.enums.RoomStatus;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<RoomDto> findAll();

    Optional<Room> findById(Long id);

    List<RoomDto> findAvailableRooms(Criteria criteria);

    Optional<Room> findAvailable(Criteria criteria);

    Optional<RoomDto> getById(Long id);

    Room update(Room room);

    RoomDto save(Room room);

    void updateStatus(Long id, RoomStatus status);
}
