package com.manager.hotel.web;

import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.service.facade.HotelFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static com.manager.hotel.web.MockData.bookingDto;
import static com.manager.hotel.web.MockData.criteria;
import static com.manager.hotel.web.MockData.dto;
import static com.manager.hotel.web.MockData.id;
import static com.manager.hotel.web.MockData.jackSparrow;
import static com.manager.hotel.web.MockData.roomDto;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelFacade facade;

    @Test
    @DisplayName("Given a request, When accessing the index page, Then it should return the index view")
    void testIndex() throws Exception {
        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("Given a request, When accessing the home page, Then it should return the home view with rooms and guests")
    void testHome() throws Exception {
        List<RoomDto> rooms = Collections.singletonList(roomDto);
        when(facade.getAllRooms()).thenReturn(rooms);
        List<GuestDto> guests = Collections.singletonList(jackSparrow);
        when(facade.getAllGuests()).thenReturn(guests);
        mockMvc.perform(get("/home"))
               .andExpect(status().isOk())
               .andExpect(model().attribute("rooms", rooms))
               .andExpect(model().attribute("guests", guests))
               .andExpect(view().name("home"));
    }

    @Test
    @DisplayName("Given a room ID, When accessing the booking page, Then it should return the booking view with the selected room")
    void testGetBooking() throws Exception {
        when(facade.findAvailableRoom(id)).thenReturn(roomDto);
        mockMvc.perform(get("/booking/" + id))
               .andExpect(status().isOk())
               .andExpect(model().attribute("room", roomDto))
               .andExpect(view().name("booking"));
    }

    @Test
    @DisplayName("Given a booking request, When saving a booking, Then it should redirect to the booking page with reservation data")
    void testSaveBooking() throws Exception {
        when(facade.saveBooking(dto)).thenReturn(bookingDto);
        mockMvc.perform(post("/booking")
                       .flashAttr("booking", dto))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/booking"))
               .andExpect(flash().attribute("reservation", bookingDto));
    }

    @Test
    @DisplayName("Given search criteria, When accessing available rooms, Then it should return the home view with filtered rooms and guests")
    void testAvailableRooms() throws Exception {
        List<RoomDto> rooms = Collections.singletonList(roomDto);
        when(facade.findAvailableRooms(criteria)).thenReturn(rooms);
        List<GuestDto> guests = Collections.singletonList(jackSparrow);
        when(facade.getAllGuests()).thenReturn(guests);

        mockMvc.perform(get("/available")
                       .flashAttr("criteria", criteria))
               .andExpect(status().isOk())
               .andExpect(model().attribute("rooms", rooms))
               .andExpect(model().attribute("guests", guests))
               .andExpect(view().name("home"));
    }

    @Test
    @DisplayName("Given a room ID and early departure request, When processing early departure, Then it should redirect to the home page")
    void testEarlyDeparture() throws Exception {
        RoomStatus roomStatus = RoomStatus.VACANT;
        boolean early = true;
        mockMvc.perform(patch("/rooms/" + id)
                       .param("roomStatus", roomStatus.toString())
                       .param("early", String.valueOf(early)))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/home"));
        verify(facade).checkOutGuest(id, early, roomStatus);
    }
}
