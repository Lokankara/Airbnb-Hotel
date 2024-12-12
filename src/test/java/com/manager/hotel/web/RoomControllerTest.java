//package com.manager.hotel.web;
//
//import com.manager.hotel.model.dto.RoomDto;
//import com.manager.hotel.service.RoomService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static com.manager.hotel.web.MockData.roomDto;
//import static com.manager.hotel.web.MockData.roomId;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//@WebMvcTest(RoomController.class)
//class RoomControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private RoomService roomService;
//
//    @Test
//    @DisplayName("Given a room ID, When accessing the room details, Then it should return the room view with room information")
//    void testCheckIn() throws Exception {
//        when(roomService.getById(roomId)).thenReturn(Optional.of(roomDto));
//        mockMvc.perform(get("/rooms/" + roomId))
//               .andExpect(status().isOk())
//               .andExpect(model().attribute("room", Optional.of(roomDto)))
//               .andExpect(view().name("room"));
//    }
//
//    @Test
//    @DisplayName("Given a room ID, When accessing the room details, Then it should return the room view with room information")
//    void checkInShouldReturnRoomViewWithRoomInformation() throws Exception {
//        when(roomService.getById(roomId)).thenReturn(Optional.ofNullable(roomDto));
//        mockMvc.perform(get("/rooms/{roomId}", roomId))
//               .andExpect(status().isOk())
//               .andExpect(view().name("room"))
//               .andExpect(model().attributeExists("room"))
//               .andExpect(model().attribute("room", Optional.ofNullable(roomDto)));
//    }
//    @Test
//    @DisplayName("Given a request to show rooms, When accessing the rooms view, Then it should return the rooms view with 'rooms' attribute")
//    void showRoomsShouldReturnRoomsViewWithRoomsAttribute() throws Exception {
//        List<RoomDto> roomDtoList = Arrays.asList(roomDto, roomDto);
//        when(roomService.findAll()).thenReturn(roomDtoList);
//        mockMvc.perform(get("/rooms"))
//               .andExpect(status().isOk())
//               .andExpect(view().name("home"))
//               .andExpect(model().attributeExists("rooms"))
//               .andExpect(model().attribute("rooms", roomDtoList));
//    }
//}
