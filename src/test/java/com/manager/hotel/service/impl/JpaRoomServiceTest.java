package com.manager.hotel.service.impl;

import com.manager.hotel.dao.RoomDao;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.model.enums.RoomType;
import com.manager.hotel.mapper.RoomMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class JpaRoomServiceTest {
    @InjectMocks
    private JpaRoomService roomService;
    @Mock
    private RoomMapper mapper;
    @Mock
    private RoomDao dao;
    private Criteria criteria;
    private Room room;
    private Room room1;
    private Room room2;
    private RoomDto roomDto1;
    private RoomDto roomDto2;
    private List<Room> rooms;
    List<RoomDto> expected;


    @BeforeEach
    void setUp() {
        room1 = Room.builder().id(1L).roomType(RoomType.SINGLE).roomStatus(RoomStatus.VACANT).capacity(1).build();
        room2 = Room.builder().id(2L).roomType(RoomType.DOUBLE).roomStatus(RoomStatus.VACANT).capacity(2).build();
        roomDto1 = RoomDto.builder().id(1L).roomType(RoomType.SINGLE).roomStatus(RoomStatus.VACANT).capacity(1).build();
        roomDto2 = RoomDto.builder().id(2L).roomType(RoomType.DOUBLE).roomStatus(RoomStatus.VACANT).capacity(2).build();
        rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);
        expected = new ArrayList<>();
        expected.add(roomDto1);
        expected.add(roomDto2);
        MockitoAnnotations.openMocks(this);
        room = Room.builder().id(2L).capacity(2).build();
        criteria = Criteria
                .builder()
                .capacity(2)
                .roomType(RoomType.SINGLE)
                .roomStatus(RoomStatus.VACANT)
                .build();
    }

    @Test
    @DisplayName("Given no rooms in the database, when findRooms is called, then return an empty list")
    void testFindRoomsWithNoRoomsInDatabase() {
        when(dao.findAll()).thenReturn(new ArrayList<>());
        List<RoomDto> roomDtos = roomService.findAll();
        assertNotNull(roomDtos);
        assertTrue(roomDtos.isEmpty());
    }

    @Test
    @DisplayName("Given rooms in the database, when findRooms is called, then return a list of room DTOs")
    void testFindRooms() {
        when(dao.findAll()).thenReturn(rooms);
        when(mapper.toListDto(rooms)).thenReturn(expected);
        List<RoomDto> roomDtos = roomService.findAll();
        assertEquals(expected, roomDtos);
        assertNotNull(roomDtos);
        assertEquals(2, roomDtos.size());
        assertEquals(room1.getId(), roomDtos.get(0).getId());
        assertEquals(room1.getCapacity(), roomDtos.get(0).getCapacity());
        assertEquals(room2.getId(), roomDtos.get(1).getId());
        assertEquals(room2.getCapacity(), roomDtos.get(1).getCapacity());
    }

    @Test
    @DisplayName("Given criteria, when findAvailableRooms is called, then return a list of available room DTOs")
    void testFindAvailableRoomsWithCriteria() {

        List<Room> availableRooms = new ArrayList<>();
        availableRooms.add(room1);
        availableRooms.add(room2);

        when(dao.findAll()).thenReturn(availableRooms);
        when(mapper.toListDto(rooms)).thenReturn(expected);
        List<RoomDto> availableRoomDtos = roomService.findAvailableRooms(criteria);

        assertNotNull(availableRoomDtos);
        assertEquals(2, availableRoomDtos.size());
        assertThat(availableRoomDtos)
                .usingRecursiveComparison()
                .isEqualTo(Arrays.asList(roomDto1, roomDto2));
    }


    @Test
    @DisplayName("Given a valid room, when update is called, then return the updated room")
    void testUpdateWithValidRoom() {
        when(dao.save(room)).thenReturn(room);
        Room updatedRoom = roomService.update(room);
        assertThat(updatedRoom).isEqualTo(room);
    }
}
