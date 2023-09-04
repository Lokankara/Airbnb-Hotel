package com.manager.hotel.service.impl;

import com.manager.hotel.dao.jpa.JpaRoomDao;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.service.RoomService;
import com.manager.hotel.service.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class JpaRoomService implements RoomService {

    private final RoomMapper mapper;
    private final JpaRoomDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> findRooms() {
        return mapper.toListDto(
                dao.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> findAvailableRooms(
            final Criteria criteria) {
        return mapper.toListDto(dao
                .findByCriteria(criteria));
    }

    @Override
    public Optional<Room> findAvailable(Criteria criteria) {
        List<Room> rooms = dao.findByCriteria(criteria);
        return rooms.isEmpty()
                ? Optional.empty()
                : Optional.of(rooms.get(0));
    }

    @Override
    @Transactional(readOnly = true)
    public RoomDto findRoomById(
            final Long id) {
        return mapper.toDto(dao.getById(id));
    }

    @Override
    public Room update(Room room) {
        return dao.update(room);
    }

    @Override
    public RoomDto findAvailableById(Long id) {
        return findRoomById(id);
    }
}
