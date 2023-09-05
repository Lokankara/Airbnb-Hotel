package com.manager.hotel.web;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.dto.PostBookingDto;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.enums.Gender;
import com.manager.hotel.model.enums.GuestStatus;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.model.enums.RoomType;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class MockData {
    public static final Long id = 1L;
    public static final Long roomId = 404L;
    public static final String firstname = "Jack";
    public static final String lastname = "Sparrow";

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final RoomDto room = RoomDto
            .builder()
            .id(roomId)
            .roomType(RoomType.SINGLE)
            .capacity(100)
            .build();

    public static final RoomDto roomDto = RoomDto
            .builder()
            .id(id)
            .roomStatus(RoomStatus.OCCUPIED)
            .roomType(RoomType.SINGLE)
            .capacity(100)
            .build();

    public static final PostBookingDto dto = PostBookingDto
            .builder()
            .checkin(LocalDate.now().atStartOfDay().format(formatter))
            .checkout(LocalDate.now().plusDays(1).atStartOfDay().format(formatter))
            .build();

    public static final GuestDto jackSparrow = GuestDto
            .builder()
            .id(id)
            .rooms(Collections.singleton(roomDto))
            .passportData(firstname + " " + lastname)
            .build();

    public static final BookingDto bookingDto = BookingDto
            .builder()
            .guest(jackSparrow)
            .id(id)
            .checkInDate(Timestamp.valueOf(dto.getCheckin()))
            .departure(Timestamp.valueOf(dto.getCheckout()))
            .build();


    public static final GuestDto willTurner = GuestDto
            .builder()
            .id(2L)
            .rooms(Collections.singleton(roomDto))
            .passportData("Will Turner")
            .build();

    public static final Guest jack = Guest
            .builder()
            .id(1L)
            .passportData("Jack Sparrow")
            .build();

    public static final Criteria criteria =
            Criteria.builder()
                    .name(firstname + " " + lastname)
                    .guestStatus(GuestStatus.ARRIVAL)
                    .gender(Gender.MAN)
                    .departure("2023-12-31")
                    .checkIn("2023-01-01")
                    .checkOut("2023-12-31")
                    .build();
}
