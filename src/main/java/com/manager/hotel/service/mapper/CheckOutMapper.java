package com.manager.hotel.service.mapper;

import com.manager.hotel.model.dto.CheckOutDto;
import com.manager.hotel.model.entity.CheckOut;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckOutMapper {

    CheckOutDto toDto(CheckOut checkOut);

    List<CheckOutDto> toListDto(List<CheckOut> checkOuts);
}
