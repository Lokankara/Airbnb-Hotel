package com.manager.hotel.web;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.manager.hotel.web.ConstantPath.BOOKING;
import static com.manager.hotel.web.ConstantPath.BOOKINGS;
import static com.manager.hotel.web.ConstantPath.RESERVATION;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    @GetMapping("/checkin")
    public String inputBookingForm(
            Model model,
            @ModelAttribute("room") RoomDto dto) {
        model.addAttribute("room", dto);
        return BOOKING;
    }

    @GetMapping("/booking")
    public String getBooking() {
        return RESERVATION;
    }

    @GetMapping("/orders")
    public String getLatestDeals(
            final Model model) {
        List<BookingDto> all = service.findAll();
        log.info(String.valueOf(all.size()));
        model.addAttribute("bookings", all);
        return BOOKINGS;
    }
}
