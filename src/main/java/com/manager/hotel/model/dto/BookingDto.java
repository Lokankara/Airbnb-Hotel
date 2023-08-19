package com.manager.hotel.model.dto;

import com.manager.hotel.model.entity.Guest;
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
public class BookingDto {
    private Long finalBill;
    private boolean earlyDeparture;
    private LocalDateTime arrival;
    private LocalDateTime departure;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private Room room;
    private Guest guest;
}
