package com.manager.hotel.service.impl;

import com.manager.hotel.dao.RoomDao;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.model.entity.RoomType;
import com.manager.hotel.exception.NoAvailableRoomsException;
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
    private final RoomDao roomRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> findRooms() {
        return mapper.toListDto(roomRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public RoomDto findAvailableRoom(
            RoomType roomType,
            int capacity) {
        List<Room> availableRooms = roomRepository
                .findByRoomTypeAndCapacity(roomType, capacity);
        return mapper.toDto(availableRooms.stream()
                .findFirst()
                .orElseThrow(() -> new NoAvailableRoomsException(
                        "No available rooms found with type %s and capacity %d"
                                .formatted(roomType, capacity))));
    }

    @Override
    @Transactional(readOnly = true)
    public Room findRoomById(Long id) {
        return roomRepository.getById(id);
    }
}
