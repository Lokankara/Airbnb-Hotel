package com.manager.hotel.service.mapper;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.entity.Booking;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoomMapper.class, PassportMapper.class})
public interface BookingMapper {

    BookingDto toDto(Booking booking);

    List<BookingDto> toListDto(List<Booking> bookings);
}
