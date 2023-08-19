package com.manager.hotel.model.dto;

import com.manager.hotel.model.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDto {

    private String passportData;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private Room room;
}
