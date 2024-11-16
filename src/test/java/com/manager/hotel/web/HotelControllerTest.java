package com.manager.hotel.web;

import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.model.dto.RoomDto;
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
import static com.manager.hotel.web.MockData.jackSparrow;
import static com.manager.hotel.web.MockData.postDto;
import static com.manager.hotel.web.MockData.roomDto;
import static com.manager.hotel.web.MockData.roomId;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

//    @Test
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
        verify(facade).getAllGuests();
    }

    @Test
    @DisplayName("Given a booking request, When saving a booking, Then it should redirect to the booking page with reservation data")
    void testSaveBooking() throws Exception {
        when(facade.saveBooking(postDto)).thenReturn(bookingDto);
        mockMvc.perform(post("/booking")
                       .flashAttr("booking", postDto))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/booking"))
               .andExpect(flash().attribute("reservation", bookingDto));
        verify(facade).saveBooking(postDto);
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
        verify(facade).getAllGuests();
    }

    @Test
    @DisplayName("Given a room ID and early departure, When departing from the room, Then it should redirect to the appropriate view")
    void testDepartureFromRoom() throws Exception {
        boolean early = true;
        when(facade.departure(roomId, early)).thenReturn(bookingDto);
        mockMvc.perform(post("/rooms")
                       .param("roomId", roomId.toString())
                       .param("early", String.valueOf(early)))
               .andExpect(status().is3xxRedirection())
               .andExpect(flash().attributeExists("reservation"))
               .andExpect(redirectedUrl("/booking"));
        verify(facade, times(1)).departure(roomId, early);
    }
}
