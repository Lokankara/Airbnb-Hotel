package com.manager.hotel.model.dto;

import com.manager.hotel.model.entity.RoomType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private int capacity;
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
}
