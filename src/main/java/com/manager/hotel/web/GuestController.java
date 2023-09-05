package com.manager.hotel.web;

import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.manager.hotel.web.ConstantPath.BOOKING;
import static com.manager.hotel.web.ConstantPath.GUESTS;

@Slf4j
@Controller
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @GetMapping
    public String getAllGuests(
            final Model model) {
        List<GuestDto> allGuests = guestService.getAllGuests();
        log.info("getAllGuests: " + (allGuests.size()));
        model.addAttribute(GUESTS, allGuests);
        return GUESTS;
    }

    @GetMapping("/{id}")
    public String getGuestById(
            final Model model,
            final @PathVariable Long id) {
        model.addAttribute("guest",
                guestService.findGuestById(id));
        return "guest";
    }

    @GetMapping("/search")
    public String searchGuestsByCriteria(
            final Model model,
            @ModelAttribute Criteria criteria) {
        log.info(criteria.toString());
        model.addAttribute(GUESTS, guestService
                .findByCriteria(criteria));
        return GUESTS;
    }

    @PatchMapping
    public String updateData(
            final Model model,
            final @ModelAttribute("guest") GuestDto dto) {
        model.addAttribute(GUESTS,
                guestService.update(dto));
        return GUESTS;
    }
}
