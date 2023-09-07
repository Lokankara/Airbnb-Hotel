package com.manager.hotel.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private Long finalBill;
    private boolean close;
    private boolean earlyDeparture;
    private Timestamp departure;
    private Timestamp checkInDate;
    private Timestamp checkOutDate;
    private Long rate;
    private Long nights;
    private RoomDto room;
    private GuestDto guest;
}
