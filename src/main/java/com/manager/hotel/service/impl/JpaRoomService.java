package com.manager.hotel.service.impl;

import com.manager.hotel.dao.jpa.JpaRoomDao;
import com.manager.hotel.exception.NoAvailableRoomsException;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.service.RoomService;
import com.manager.hotel.service.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class JpaRoomService implements RoomService {

    private final RoomMapper mapper;
    private final JpaRoomDao roomRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> findRooms() {
        return mapper.toListDto(roomRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Room findAvailableRoom(
            final Criteria criteria) {
        List<Room> availableRooms = roomRepository
                .findByRoomTypeAndCapacity(criteria);
        return availableRooms.stream()
                .findFirst()
                .orElseThrow(() -> new NoAvailableRoomsException(
                        "No available rooms found by criteria: "
                                + criteria));
    }

    @Override
    @Transactional(readOnly = true)
    public Room findRoomById(
            final Long id) {
        return roomRepository
                .getById(id);
    }
}
