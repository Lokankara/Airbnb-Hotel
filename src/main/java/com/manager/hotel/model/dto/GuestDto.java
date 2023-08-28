package com.manager.hotel.model.dto;

import com.manager.hotel.model.entity.Room;
import com.manager.hotel.model.enums.Gender;
import com.manager.hotel.model.enums.GuestStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestDto {

    private Long id;
    private String passportData;
    private Timestamp checkIn;
    private Timestamp arrivalDate;
    private Timestamp departureDate;
    @Enumerated(EnumType.STRING)
    private GuestStatus guestStatus;
    private Set<Room> rooms;
    private String feedback;
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
