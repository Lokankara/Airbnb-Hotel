package com.manager.hotel.model.dto;

import com.manager.hotel.model.entity.Guest;
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
public class CheckOutDto {
    private Guest guest;
    private double finalBill;
    private boolean earlyDeparture;
    private LocalDateTime checkOutDate;
}
