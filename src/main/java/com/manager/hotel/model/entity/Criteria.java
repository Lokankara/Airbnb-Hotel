package com.manager.hotel.model.entity;

import com.manager.hotel.model.dto.PostBookingDto;
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
    private Room room;
    private Guest guest;
    private PostBookingDto booking;
    private Passport passport;
}
