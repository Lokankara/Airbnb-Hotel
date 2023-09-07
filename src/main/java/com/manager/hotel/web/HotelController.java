package com.manager.hotel.web;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.dto.PostBookingDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.service.facade.HotelFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.manager.hotel.web.ConstantPath.GUESTS;
import static com.manager.hotel.web.ConstantPath.HOME;
import static com.manager.hotel.web.ConstantPath.REDIRECT;
import static com.manager.hotel.web.ConstantPath.RESERVATION;
import static com.manager.hotel.web.ConstantPath.ROOMS;

@Slf4j
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
        return HOME;
    }

    @PostMapping("/booking")
    public String saveBooking(
            @ModelAttribute("booking") final PostBookingDto postDto,
            RedirectAttributes attributes) {
        BookingDto dto = facade.saveBooking(postDto);
        attributes.addFlashAttribute(RESERVATION, dto);
        return REDIRECT;
    }

    @GetMapping("/available")
    public String availableRooms(
            @ModelAttribute("criteria") Criteria criteria,
            Model model) {
        model.addAttribute(ROOMS, facade
                .findAvailableRooms(criteria));
        model.addAttribute(GUESTS,
                facade.getAllGuests());
        return HOME;
    }

    @PostMapping("/rooms")
    public String departureFromRoom(
            RedirectAttributes attributes,
            final @RequestParam Long roomId,
            final @RequestParam(name = "early",
                    required = false,
                    defaultValue = "false")
            boolean early) {
        BookingDto dto = facade.departure(roomId, early);
        attributes.addFlashAttribute(RESERVATION, dto);
        return REDIRECT;
    }

    @PostMapping("/rooms/{roomId}")
    public String updatedStatusRoom(
            final @PathVariable Long roomId,
            final @RequestParam RoomStatus roomStatus) {
        facade.updatedRoom(roomId, roomStatus);
        return HOME;
    }
}
