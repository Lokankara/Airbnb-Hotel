package com.manager.hotel.mapper;

import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.model.dto.BookingDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoomMapper.class, PassportMapper.class})
public interface BookingRoomMapper {

    BookingDto toDto(Booking booking);

    List<BookingDto> toListDto(List<Booking> bookings);
}
