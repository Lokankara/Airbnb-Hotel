package com.manager.hotel.service.facade;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.dto.PostBookingDto;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.service.BookingService;
import com.manager.hotel.service.GuestService;
import com.manager.hotel.service.PassportService;
import com.manager.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDate.parse;
import static java.time.LocalDateTime.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelFacade {
    private final RoomService roomService;
    private final GuestService guestService;
    private final BookingService bookingService;
    private final PassportService passportService;

    public List<GuestDto> getAllGuests() {
        return guestService.getAllGuests();
    }

    public List<RoomDto> getAllRooms() {
        return roomService.findRooms();
    }

    public BookingDto checkInGuest(
            final Long guestId, final Long roomId) {
        Guest guest = guestService.findGuestById(guestId);
        Room room = roomService.findRoomById(roomId); // TODO
        guest.addRoom(room);
        return bookingService.checkInGuest(guest);
    }

    public BookingDto checkOutGuest(
            final Long guestId,
            final boolean earlyDeparture) {
        return bookingService.checkOutGuest(
                guestId, earlyDeparture);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveBooking(
            final PostBookingDto dto) {
        Room room = getRoom(dto);
        Guest guest = getGuest(dto, room);
        Guest saved = guestService.save(guest);
        Passport passport = getByFullName(dto, saved);
        saved.setPassport(passport);
        bookingService.checkInGuest(saved);
    }

    private Passport getByFullName(
            PostBookingDto dto, Guest saved) {
        return passportService.findByFirstNameAndLastName(
                                      dto.getFirstname(), dto.getLastname())
                              .orElse(passportService.save(
                                      getPassport(dto, saved)));
    }

    private static Passport getPassport(
            PostBookingDto dto, Guest saved) {
        return Passport.builder()
                       .email(dto.getEmail())
                       .address(dto.getAddress())
                       .creditCard(dto.getCreditCard())
                       .phone(dto.getPhone())
                       .email(dto.getEmail())
                       .gender(dto.getGender())
                       .guest(saved)
                       .firstName(dto.getFirstname())
                       .lastName(dto.getLastname())
                       .build();
    }

    private static Guest getGuest(
            final PostBookingDto dto,
            final Room room) {
        return Guest.builder()
                    .arrivalDate(valueOf(now()))
                    .checkIn(valueOf(parse(
                            dto.getCheckin())
                            .atStartOfDay()))
                    .guestStatus(dto.getGuestStatus())
                    .gender(dto.getGender())
                    .departureDate(valueOf(parse(
                            dto.getCheckout())
                            .atStartOfDay()))
                    .passportData(dto.getFirstname() + " " + dto.getLastname())
                    .rooms(Collections.singleton(room))
                    .build();
    }

    private Room getRoom(PostBookingDto dto) {
        Room room = roomService
                .findAvailableRoom(getCriteria(dto));
        room.setRoomStatus(RoomStatus.OCCUPIED);
        return room;
    }

    private static Criteria getCriteria(
            PostBookingDto dto) {
        return Criteria
                .builder()
                .room(Room.builder()
                          .capacity(dto.getCapacity())
                          .roomType(dto.getRoomType())
                          .roomStatus(RoomStatus.VACANT)
                          .build())
                .booking(dto)
                .build();
    }
}
