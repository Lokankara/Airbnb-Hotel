package com.manager.hotel.service.facade;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.dto.PostBookingDto;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Booking;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public BookingDto checkOutGuest(
            final Long guestId,
            final boolean earlyDeparture) {
        return bookingService.checkOutGuest(
                guestId, earlyDeparture);
    }

    public RoomDto findAvailableRoom(Long id) {
        return roomService.findAvailableById(id);
    }

    public List<RoomDto> findAvailableRooms(Criteria criteria) {
        return roomService.findAvailableRooms(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public BookingDto saveBooking(final PostBookingDto dto) {
        Optional<Room> optional = roomService.findAvailable(getCriteria(dto));
        if (optional.isPresent()) {
            Room room = optional.get();
            Guest guest = guestService.save(getGuest(dto, room));
            Passport passport = passportService
                    .findByFirstNameAndLastName(dto.getFirstname(), dto.getLastname())
                    .orElseGet(() -> passportService.save(getPassport(dto, guest)));
            room.setRoomStatus(RoomStatus.OCCUPIED);
            room.setGuest(guest);
            guest.setPassport(passport);
            Guest savedGuest = guestService
                    .findGuestById(guest.getId());
            savedGuest.setPassport(passport);
            savedGuest.addRoom(room);
            guestService.update(savedGuest);
            BookingDto booking = bookingService
                    .save(getBooking(dto, guest));
            roomService.update(room);
            return booking;
        } else {
            return new BookingDto();
        }
    }

    private static Booking getBooking(
            PostBookingDto dto, Guest guest) {
        return Booking.builder()
                      .checkInDate(valueOf(parse(dto.getCheckin()).atStartOfDay()))
                      .checkOutDate(valueOf(parse(dto.getCheckout()).atStartOfDay()))
                      .guest(guest)
                      .build();
    }

    private static Passport getPassport(PostBookingDto dto, Guest guest) {
        return Passport.builder()
                       .firstName(dto.getFirstname())
                       .lastName(dto.getLastname())
                       .address(dto.getAddress())
                       .creditCard(dto.getCreditCard())
                       .email(dto.getEmail())
                       .phone(dto.getPhone())
                       .gender(dto.getGender())
                       .guest(guest)
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

    private static Criteria getCriteria(
            PostBookingDto dto) {
        return Criteria
                .builder()
                .capacity(dto.getCapacity())
                .roomType(dto.getRoomType())
                .roomStatus(RoomStatus.VACANT)
                .build();
    }
}
