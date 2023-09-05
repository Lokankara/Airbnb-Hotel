package com.manager.hotel.web;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.model.dto.PostBookingDto;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.service.facade.HotelFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.manager.hotel.web.ConstantPath.BOOKING;
import static com.manager.hotel.web.ConstantPath.GUESTS;
import static com.manager.hotel.web.ConstantPath.HOME;
import static com.manager.hotel.web.ConstantPath.ROOM;
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

    @GetMapping("/booking/{id}")
    public String getBooking(
            final Model model,
            final @PathVariable Long id) {
        RoomDto roomDto = facade.findAvailableRoom(id);
        model.addAttribute(ROOM, roomDto);
        return BOOKING;
    }

    @PostMapping("/booking")
    public String saveBooking(
            RedirectAttributes redirectAttributes,
            @ModelAttribute("booking") final PostBookingDto dto) {
        BookingDto bookingDto = facade.saveBooking(dto);
        redirectAttributes.addFlashAttribute("reservation", bookingDto);
        return "redirect:/booking";
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

    @PatchMapping("/rooms/{roomId}")
    public String earlyDeparture(
            final @PathVariable Long roomId,
            final @RequestParam(name = "roomStatus") RoomStatus roomStatus,
            final @RequestParam(name = "early") boolean early) {
        facade.checkOutGuest(roomId, early, roomStatus);
        return "redirect:/home";
    }
}
