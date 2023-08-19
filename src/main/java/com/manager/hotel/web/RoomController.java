package com.manager.hotel.web;

import com.manager.hotel.model.entity.RoomType;
import com.manager.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.manager.hotel.web.ConstantPath.AVAILABLE;
import static com.manager.hotel.web.ConstantPath.ROOMS;

@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public String showRooms(
            final Model model) {
        model.addAttribute(ROOMS,
                roomService.findRooms());
        return ROOMS;
    }

    @GetMapping("/available")
    public String showAvailableRooms(
            final Model model,
            final @RequestParam RoomType type,
            final @RequestParam int capacity) {
        model.addAttribute(AVAILABLE, roomService
                .findAvailableRoom(type, capacity));
        return AVAILABLE;
    }
}
