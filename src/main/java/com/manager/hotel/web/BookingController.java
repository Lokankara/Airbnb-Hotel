package com.manager.hotel.web;

import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;

import static com.manager.hotel.web.ConstantPath.BOOKING;
import static com.manager.hotel.web.ConstantPath.BOOKINGS;
import static com.manager.hotel.web.ConstantPath.RESERVATION;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    @GetMapping("/checkin")
    public String inputBookingForm(
            Model model, @ModelAttribute("room") RoomDto dto) {
        model.addAttribute("room", dto);
        return BOOKING;
    }

    @GetMapping("/booking")
    public String getBooking() {
        return RESERVATION;
    }

    @GetMapping("/latest")
    public String getLatestDeals(
            final Model model,
            final Timestamp fromDate) {
        model.addAttribute(
                "bookings",
                service.getLatest(fromDate));
        return BOOKINGS;
    }
}
