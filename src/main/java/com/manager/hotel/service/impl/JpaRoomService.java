package com.manager.hotel.service.impl;

import com.manager.hotel.dao.jpa.JpaRoomDao;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.service.RoomService;
import com.manager.hotel.service.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class JpaRoomService implements RoomService {

    private final RoomMapper mapper;
    private final JpaRoomDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> findAll() {
        return mapper.toListDto(
                dao.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDto> findAvailableRooms(
            final Criteria criteria) {
        return mapper.toListDto(
                dao.findByCriteria(criteria));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Room> findAvailable(
            final Criteria criteria) {
        List<Room> rooms = dao.findByCriteria(criteria);
        return rooms.isEmpty()
                ? Optional.empty()
                : Optional.of(rooms.get(0));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Room> findById(
            final Long id) {
        return dao.findRoomById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Room update(
            final Room room) {
        return dao.update(room).orElse(new Room());
    }

    @Override
    public RoomDto save(
            final Room room) {
        return mapper.toDto(
                dao.save(room));
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
    public Optional<RoomDto> getById(
            final Long id) {
        return Optional.of(mapper
                .toDto(findById(id)
                        .orElse(new Room())));
    }
}
