package com.manager.hotel.mapper;

import com.manager.hotel.dto.ReadUserDTO;
import com.manager.hotel.entity.Authority;
import com.manager.hotel.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ReadUserDTO readUserDTOToUser(User user);

    default String mapAuthoritiesToString(Authority authority) {
        return authority.getName();
    }
}
