package com.manager.hotel.service.mapper;

import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.entity.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PassportMapper.class, BookingMapper.class})
public interface GuestMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "passport", source = "passport")
    GuestDto toDto(Guest guest);

    Guest toEntity(GuestDto dto);

    List<GuestDto> toListDto(List<Guest> guests);
}
