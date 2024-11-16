package com.manager.hotel.service.impl;

import com.manager.hotel.dao.RoomDao;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.service.RoomService;
import com.manager.hotel.mapper.RoomMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class JpaRoomService implements RoomService {

    private final RoomDao dao;
    private final RoomMapper mapper;

    @Override
    public List<RoomDto> findAll() {
        return mapper.toListDto(dao.findAll());
    }

    @Override
    public List<RoomDto> findAvailableRooms(final Criteria criteria) {
        return mapper.toListDto(dao.findAll());
    }

    @Override
    public Optional<Room> findAvailable(final Criteria criteria) {
        List<Room> rooms = dao.findAll();
        return rooms.isEmpty()
                ? Optional.empty()
                : Optional.of(rooms.getFirst());
    }

    @Override
    public Optional<Room> findById(final Long id) {
        return dao.findById(id);
    }

    @Override
    public Room update(final Room room) {
        return dao.save(room);
    }

    @Override
    public RoomDto save(final Room room) {
        return mapper.toDto(dao.save(room));
    }

    @Override
    public void updateStatus(Long id, RoomStatus status) {
        Optional<Room> optionalRoom = findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            if (!status.equals(RoomStatus.OCCUPIED)
                    && (!room.getRoomStatus().equals(RoomStatus.OCCUPIED))) {
                room.setRoomStatus(status);
                update(room);
            }
        }
    }

    @Override
    public Optional<RoomDto> getById(final Long id) {
        return Optional.of(mapper.toDto(findById(id)
                .orElse(new Room())));
    }
}
