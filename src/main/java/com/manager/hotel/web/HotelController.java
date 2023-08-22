package com.manager.hotel.web;

import com.manager.hotel.service.facade.HotelFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.manager.hotel.web.ConstantPath.BOOKING;
import static com.manager.hotel.web.ConstantPath.GUESTS;
import static com.manager.hotel.web.ConstantPath.ROOMS;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HotelController {
    private final HotelFacade facade;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute(ROOMS,
                facade.getAllRooms());
        model.addAttribute(GUESTS,
                facade.getAllGuests());
        return "home";
    }

    @PostMapping("/booking")
    public String getAllBooking(
            final Model model) {
        model.addAttribute(BOOKING,
                facade.getAllBooking());
        return BOOKING;
    }

    @PostMapping("/guests/{guestId}/rooms/{roomId}")
    public String checkIn(
            final Model model,
            final @PathVariable Long guestId,
            final @PathVariable Long roomId) {
        model.addAttribute(BOOKING,
                facade.checkInGuest(guestId, roomId));
        return BOOKING;
    }

    @DeleteMapping("/guests/{guestId}")
    public String earlyDeparture(
            final Model model,
            final @PathVariable Long guestId,
            final @RequestParam boolean early) {
        model.addAttribute(BOOKING,
                facade.checkOutGuest(guestId, early));
        return BOOKING;
    }
}
