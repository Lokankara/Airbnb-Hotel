package com.manager.hotel.mapper;

import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.entity.Guest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PassportMapper.class, BookingRoomMapper.class})
public interface GuestMapper {

    GuestDto toDto(Guest guest);

    Guest toEntity(GuestDto dto);

    List<GuestDto> toListDto(List<Guest> guests);
}
