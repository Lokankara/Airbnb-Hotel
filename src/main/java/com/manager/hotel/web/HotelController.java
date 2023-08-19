package com.manager.hotel.web;

import com.manager.hotel.model.dto.CheckOutDto;
import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.service.HotelFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HotelController {
    private final HotelFacade hotelFacade;
    private static final String GUESTS = "guests";

    @GetMapping
    public String home(Model model) {
        List<GuestDto> guests = hotelFacade.getAllGuests();
        List<RoomDto> rooms = hotelFacade.getAllRooms();
        model.addAttribute("rooms", rooms);
        model.addAttribute(GUESTS, guests);
        return "home";
    }

    @GetMapping("/guests")
    public String getAllGuests(Model model) {
        List<GuestDto> guests = hotelFacade.getAllGuests();
        model.addAttribute(GUESTS, guests);
        return GUESTS;
    }

    @GetMapping("/search")
    public String searchGuestsByPassportData(Model model,
            final @RequestParam String passportData) {
        List<GuestDto> guests = hotelFacade
                .searchGuestsByPassportData(passportData);
        model.addAttribute(GUESTS, guests);
        return GUESTS;
    }

    @GetMapping("/find")
    public String findGuestsByCharacteristic(
            Model model,
            @RequestParam("characteristic") String characteristic) {
        List<GuestDto> guests = hotelFacade.findGuests(characteristic);
        model.addAttribute(GUESTS, guests);
        return GUESTS;
    }

    @GetMapping("/departing")
    public String findGuestsDepartingToday(Model model) {
        List<GuestDto> departingGuests = hotelFacade.findDepartingToday();
        model.addAttribute(GUESTS, departingGuests);
        return GUESTS;
    }

    @PostMapping("/checkin")
    public String checkIn(Model model,
            @RequestParam Long guestId,
            @RequestParam Long roomId) {
        GuestDto guestDto = hotelFacade.checkInGuest(guestId, roomId);
        model.addAttribute("guest", guestDto);
        return "guest";
    }

    @PostMapping("/checkout")
    public String checkOutGuest(Model model,
            final @RequestParam Long guestId,
            final @RequestParam boolean earlyDeparture) {
        CheckOutDto checkOut = hotelFacade
                .checkOutGuest(guestId, earlyDeparture);
        model.addAttribute("checkoutDto", checkOut);
        return "checkOut";
    }
}
