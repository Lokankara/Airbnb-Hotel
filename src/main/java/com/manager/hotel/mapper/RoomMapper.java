package com.manager.hotel.mapper;

import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Room;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomDto toDto(Room room);

    List<RoomDto> toListDto(List<Room> rooms);
}
