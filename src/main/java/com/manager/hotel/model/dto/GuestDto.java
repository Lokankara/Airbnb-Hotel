package com.manager.hotel.model.dto;

import com.manager.hotel.model.enums.GuestStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private GuestStatus guestStatus;
    private RoomDto room;
}
