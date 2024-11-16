package com.manager.hotel.service.facade;

import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.dto.PostBookingDto;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.model.enums.RoomStatus;
//import com.manager.hotel.service.PassportService;
import com.manager.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.sql.Timestamp.valueOf;
import static java.time.Duration.between;
import static java.time.LocalDate.parse;
import static java.time.LocalDateTime.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelFacade {
    private final RoomService roomService;
//    private final GuestService guestService;
//    private final BookingService bookingService;
//    private final PassportService passportService;

    public List<GuestDto> getAllGuests() {
        return new ArrayList<>();
//        return guestService.getAllGuests();
    }

    public List<RoomDto> getAllRooms() {
        return new ArrayList<>();
//        return roomService.findAll();
    }

    public BookingDto departure(Long id, boolean early) {
        Optional<Room> optionalRoom = roomService.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            Booking booking = room.getBooking();
            if (booking != null) {
                booking.setCheckOutDate(new Timestamp(System.currentTimeMillis()));
                booking.setEarlyDeparture(early);
                if (early) {
                    booking.setFinalBill(booking.getFinalBill() - booking.getRate());
                }
                room.setRoomStatus(RoomStatus.VACANT);
                room.setBooking(null);
                room.setGuest(null);
//                roomService.update(room);
                booking.setRoom(null);
                booking.setClose(Boolean.TRUE);
                booking.setDeparture(Timestamp.valueOf(now()));
//                return bookingService.update(booking);
                return new BookingDto();
            }
        }
        return new BookingDto();
    }

    public List<RoomDto> findAvailableRooms(
            final Criteria criteria) {
//        return roomService.findAvailableRooms(criteria);
        return new ArrayList<>();
    }

    @Transactional(rollbackFor = Exception.class)
    public BookingDto saveBooking(final PostBookingDto dto) {
//        Optional<Room> optional = roomService.findAvailable(getCriteria(dto));
        Optional<Room> optional = Optional.empty();

        if (optional.isPresent()) {
            Room room = optional.get();
//            Guest guest = guestService.findByFullName(dto.getFirstname(), dto.getLastname())
//                    .orElseGet(() -> guestService.save(getGuest(dto, room)));
            Guest guest = new Guest();
//            Passport passport = passportService
//                    .findByFirstNameAndLastName(dto.getFirstname(), dto.getLastname())
//                    .orElseGet(() -> passportService.save(getPassport(dto, guest)));
            Passport passport = new Passport();
            guest.setCheckIn(valueOf(parse(dto.getCheckin()).atStartOfDay()));
            guest.setCheckOut(valueOf(parse(dto.getCheckout()).atStartOfDay()));
            guest.setPassport(passport);
            room.setRoomStatus(RoomStatus.OCCUPIED);
            room.setGuest(guest);

            long nights = getNights(room);
            long rate = room.getRoomType().getRate();
            Booking booking = getBooking(guest, room, nights, rate);
//            guestService.update(guest);
//            Booking save = bookingService.save(booking);
            Booking save = new Booking();
            room.setBooking(save);
//            roomService.update(room);
//            return bookingService.toDto(save);
            return new BookingDto();
        } else {
            return new BookingDto();
        }
    }

    public void updatedRoom(
            final Long id,
            final RoomStatus status) {
//        roomService.updateStatus(id, status);
    }

    private static long getNights(Room room) {
        return between(
                room.getGuest().getCheckIn().toInstant(),
                room.getGuest().getCheckOut().toInstant())
                .toDays() + 1;
    }

    private static Booking getBooking(
            Guest guest, Room room,
            Long nights, Long rate) {
        return Booking.builder()
                .nights(nights)
                .rate(rate)
                .room(room)
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

    static Criteria getCriteria(
            PostBookingDto dto) {
        return Criteria
                .builder()
                .capacity(dto.getCapacity())
                .roomType(dto.getRoomType())
                .roomStatus(RoomStatus.VACANT)
                .build();
    }
}
