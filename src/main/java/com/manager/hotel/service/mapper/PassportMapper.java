package com.manager.hotel.service.mapper;

import com.manager.hotel.model.dto.PassportDto;
import com.manager.hotel.model.entity.Passport;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassportMapper {

    PassportDto toDto(Passport passport);

    List<PassportDto> toListDto(List<Passport> passports);
}
