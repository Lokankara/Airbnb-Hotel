package com.manager.hotel.web;

import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.entity.RoomType;
import com.manager.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public String showRooms(Model model) {
        model.addAttribute("rooms",
                roomService.getAllRooms());
        return "rooms";
    }

    @GetMapping("/available")
    public String showAvailableRooms(
            Model model,
            @RequestParam RoomType roomType,
            @RequestParam int capacity) {
        RoomDto availableRoom = roomService
                .findAvailableRoom(roomType, capacity);
        model.addAttribute(
                "availableRoom", availableRoom);
        return "available";
    }
}
