package com.manager.hotel.service.mapper;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RoomMapper.class, PassportMapper.class})
public interface BookingMapper {
    @Mapping(target = "departure", source = "departure", dateFormat = "yyyy-MM-dd HH:mm:ss.SSS")
    @Mapping(target = "checkInDate", source = "checkInDate", dateFormat = "yyyy-MM-dd HH:mm:ss.SSS")
    @Mapping(target = "checkOutDate", source = "checkOutDate", dateFormat = "yyyy-MM-dd HH:mm:ss.SSS")
    BookingDto toDto(Booking booking);

    List<BookingDto> toListDto(List<Booking> bookings);
}
