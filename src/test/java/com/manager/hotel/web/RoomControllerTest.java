package com.manager.hotel.web;

import com.manager.hotel.service.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.manager.hotel.web.MockData.room;
import static com.manager.hotel.web.MockData.roomId;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Test
    @DisplayName("Given a room ID, When accessing the room details, Then it should return the room view with room information")
    void testCheckIn() throws Exception {
        when(roomService.getRoomById(roomId)).thenReturn(room);
        mockMvc.perform(get("/rooms/" + roomId))
               .andExpect(status().isOk())
               .andExpect(model().attribute("room", room))
               .andExpect(view().name("room"));
    }
}
