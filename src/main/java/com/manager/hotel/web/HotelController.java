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
    public String home(Model model) {
        model.addAttribute(ROOMS, facade
                .getAllRooms());
        model.addAttribute(GUESTS, facade
                .getAllGuests());
        return "home";
    }

    @GetMapping("/guests")
    public String getAllGuests(
            final Model model) {
        model.addAttribute(GUESTS, facade
                .getAllGuests());
        return GUESTS;
    }

    @GetMapping("/search")
    public String searchGuestsByPassportData(
            final Model model,
            final @RequestParam String data) {
        model.addAttribute(GUESTS, facade
                .searchByPassportData(data));
        return GUESTS;
    }

    @GetMapping("/find")
    public String findGuestsByCharacteristic(
            final Model model,
            final @RequestParam("characteristic")
            String characteristic) {
        model.addAttribute(GUESTS, facade
                .findGuests(characteristic));
        return GUESTS;
    }

    @GetMapping("/departing")
    public String findGuestsDepartingToday(
            final Model model) {
        model.addAttribute(GUESTS, facade
                .findDepartingToday());
        return GUESTS;
    }

    @PostMapping("/guests/{guestId}/rooms/{roomId}")
    public String checkIn(
            final Model model,
            final @PathVariable Long guestId,
            final @PathVariable Long roomId) {
        model.addAttribute(BOOKING, facade
                .checkInGuest(guestId, roomId));
        return BOOKING;
    }

    @DeleteMapping("/guests/{guestId}")
    public String checkOutGuest(
            final Model model,
            final @PathVariable Long guestId,
            final @RequestParam boolean earlyDeparture) {
        model.addAttribute(BOOKING, facade
                .checkOutGuest(guestId, earlyDeparture));
        return BOOKING;
    }
}
