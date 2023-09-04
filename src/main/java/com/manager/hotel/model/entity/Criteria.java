package com.manager.hotel.model.entity;

import com.manager.hotel.model.enums.Gender;
import com.manager.hotel.model.enums.GuestStatus;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.model.enums.RoomType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {
    private Long id;
    private Integer capacity;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
    @Enumerated(EnumType.STRING)
    private GuestStatus guestStatus;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String name;
    private String checkIn;
    private String checkOut;
}

