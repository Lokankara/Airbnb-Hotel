package com.manager.hotel.model.dto;

import com.manager.hotel.model.entity.Guest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private Long finalBill;
    private boolean earlyDeparture;
    private Timestamp arrival;
    private Timestamp checkInDate;
    private Timestamp checkOutDate;
    private Guest guest;
}
