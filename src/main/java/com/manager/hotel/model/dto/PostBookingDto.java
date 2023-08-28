package com.manager.hotel.model.dto;

import com.manager.hotel.model.enums.Gender;
import com.manager.hotel.model.enums.GuestStatus;
import com.manager.hotel.model.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostBookingDto {
    private String firstname;
    private String lastname;
    private String address;
    private String creditCard;
    private String checkin;
    private String checkout;
    private Integer capacity;
    private Integer adults;
    private String email;
    private String phone;
    private Gender gender;
    private RoomType roomType;
    private GuestStatus guestStatus;
}
