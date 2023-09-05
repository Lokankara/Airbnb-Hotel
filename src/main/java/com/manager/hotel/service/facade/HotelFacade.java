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

import java.time.Duration;
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

    public void checkOutGuest(
            Long roomId, boolean early, RoomStatus status) {
        Optional<Room> optionalRoom = roomService.findRoomById(roomId);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            Booking booking = bookingService.findByRoomId(roomId);
            booking.setEarlyDeparture(early);
            room.setRoomStatus(status);
            log.info(booking.getGuest().toString());
            log.info(room.getGuest().toString());
            if (early){
                Guest guest = booking.getGuest();
                guest.removeRoom(room);
                guestService.update(guest);
            }
            roomService.update(room);
        }
    }

    public RoomDto findAvailableRoom(Long id) {
        return roomService.getRoomById(id);
    }

    public List<RoomDto> findAvailableRooms(Criteria criteria) {
        return roomService.findAvailableRooms(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public BookingDto saveBooking(final PostBookingDto dto) {
        Optional<Room> optional = roomService.findAvailable(getCriteria(dto));
        if (optional.isPresent()) {
            Room room = optional.get();
            Guest guest = guestService.findByFullName(dto.getFirstname(), dto.getLastname())
                                      .orElseGet(() -> guestService.save(getGuest(dto, room)));
            Passport passport = passportService
                    .findByFirstNameAndLastName(dto.getFirstname(), dto.getLastname())
                    .orElseGet(() -> passportService.save(getPassport(dto, guest)));

            guest.setCheckIn(valueOf(parse(dto.getCheckin()).atStartOfDay()));
            guest.setCheckOut(valueOf(parse(dto.getCheckout()).atStartOfDay()));
            guest.setPassport(passport);
            room.setRoomStatus(RoomStatus.OCCUPIED);
            room.setGuest(guest);
            guestService.update(guest);
            roomService.update(room);
            long nights = getNights(room);
            long rate = room.getRoomType().getRate();
            return bookingService.save(getBooking(guest, nights, rate));
        } else {
            return new BookingDto();
        }
    }

    private static long getNights(Room room) {
        return Duration.between(
                room.getGuest().getCheckIn().toInstant(),
                room.getGuest().getCheckOut().toInstant()).toDays() + 1;
    }

    private static Booking getBooking(
            Guest guest, Long nights, Long rate) {
        return Booking.builder()
                      .nights(nights)
                      .rate(rate)
                      .finalBill(nights * rate)
                      .checkInDate(guest.getCheckIn())
                      .checkOutDate(guest.getCheckOut())
                      .guest(guest)
                      .build();
    }

    private static Passport getPassport(
            PostBookingDto dto, Guest guest) {
        return Passport.builder()
                       .firstname(dto.getFirstname())
                       .lastname(dto.getLastname())
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
                    .departure(valueOf(now()))
                    .checkIn(valueOf(parse(
                            dto.getCheckin())
                            .atStartOfDay()))
                    .guestStatus(dto.getGuestStatus())
                    .gender(dto.getGender())
                    .checkOut(valueOf(parse(
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
