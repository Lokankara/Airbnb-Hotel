package com.manager.hotel.web;

import com.manager.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;

import static com.manager.hotel.web.ConstantPath.BOOKING;
import static com.manager.hotel.web.ConstantPath.BOOKINGS;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    @GetMapping("/booking")
    public String inputBookingForm() {
        return BOOKING;
    }

    @GetMapping("/checkin")
    public String getAllBooking(
            final Model model) {
        model.addAttribute(
                "bookings",
                service.findAll());
        return BOOKINGS;
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
