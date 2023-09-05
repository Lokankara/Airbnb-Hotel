package com.manager.hotel.web;

import com.manager.hotel.model.dto.BookingDto;
import com.manager.hotel.service.BookingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static com.manager.hotel.web.MockData.bookingDto;
import static com.manager.hotel.web.MockData.roomDto;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService service;

    @Test
    @DisplayName("When accessing the check-in page with a room, Then the booking page should be displayed with the room attributes")
    void testInputBookingForm() throws Exception {
        mockMvc.perform(get("/checkin")
                       .flashAttr("room", roomDto))
               .andExpect(status().isOk())
               .andExpect(model().attribute("room", roomDto))
               .andExpect(view().name("booking"));
    }

    @Test
    @DisplayName("When accessing the booking page, Then the reservation page should be displayed")
    void testGetBooking() throws Exception {
        mockMvc.perform(get("/booking"))
               .andExpect(status().isOk())
               .andExpect(view().name("reservation"));
    }

    @Test
    @DisplayName("When accessing the orders page, Then the list of bookings should be displayed")
    void testGetLatestDeals() throws Exception {
        List<BookingDto> bookings = Collections.singletonList(bookingDto);
        when(service.findAll()).thenReturn(bookings);
        mockMvc.perform(get("/orders"))
               .andExpect(status().isOk())
               .andExpect(model().attribute("bookings", bookings))
               .andExpect(view().name("bookings"));
    }
}
