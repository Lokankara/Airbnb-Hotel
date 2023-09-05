package com.manager.hotel.web;

import com.manager.hotel.model.dto.GuestDto;
import com.manager.hotel.service.GuestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.manager.hotel.web.MockData.criteria;
import static com.manager.hotel.web.MockData.id;
import static com.manager.hotel.web.MockData.jack;
import static com.manager.hotel.web.MockData.jackSparrow;
import static com.manager.hotel.web.MockData.willTurner;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GuestController.class)
class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GuestService guestService;

    @Test
    @DisplayName("Given a request to retrieve all guests, When invoking getAllGuests(), Then it should return a list of all guests")
    void testGetAllGuests() throws Exception {
        List<GuestDto> allGuests = Arrays.asList(jackSparrow, willTurner);
        when(guestService.getAllGuests()).thenReturn(allGuests);
        mockMvc.perform(get("/guests"))
               .andExpect(status().isOk())
               .andExpect(model().attribute("guests", allGuests))
               .andExpect(view().name("guests"));
    }

    @Test
    @DisplayName("Given a guest ID, When invoking findGuestById(), Then it should return the guest with the specified ID")
    void testGetGuestById() throws Exception {
        when(guestService.findGuestById(id)).thenReturn(jack);
        mockMvc.perform(get("/guests/" + id))
               .andExpect(status().isOk())
               .andExpect(model().attribute("guest", jack))
               .andExpect(view().name("guest"));
    }

    @Test
    @DisplayName("Given search criteria, When invoking findByCriteria(), Then it should return a list of guests matching the criteria")
    void testSearchGuestsByCriteria() throws Exception {
        List<GuestDto> guests = Collections.singletonList(jackSparrow);
        when(guestService.findByCriteria(criteria)).thenReturn(guests);

        mockMvc.perform(get("/guests/search")
                       .flashAttr("criteria", criteria))
               .andExpect(status().isOk())
               .andExpect(model().attribute("guests", guests))
               .andExpect(view().name("guests"));
    }

    @Test
    @DisplayName("Given a guest to update, When invoking update(), Then it should return the updated guest")
    void testUpdateData() throws Exception {
        when(guestService.update(jackSparrow)).thenReturn(willTurner);
        mockMvc.perform(patch("/guests")
                       .flashAttr("guest", jackSparrow))
               .andExpect(status().isOk())
               .andExpect(model().attribute("guests", willTurner))
               .andExpect(view().name("guests"));
    }
}
