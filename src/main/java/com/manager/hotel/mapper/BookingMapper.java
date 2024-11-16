package com.manager.hotel.mapper;

import com.manager.hotel.model.dto.BookedDateDTO;
import com.manager.hotel.model.dto.NewBookingDTO;
import com.manager.hotel.model.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking newBookingToBooking(NewBookingDTO newBookingDTO);

    BookedDateDTO bookingToCheckAvailability(Booking booking);
}
