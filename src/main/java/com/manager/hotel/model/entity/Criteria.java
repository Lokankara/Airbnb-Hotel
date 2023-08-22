package com.manager.hotel.model.entity;

import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.model.enums.RoomType;
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

    private int capacity;
    private String duration;
    private Passport passport;
    private boolean isArrived;
    private RoomStatus status;
    private RoomType type;
}
