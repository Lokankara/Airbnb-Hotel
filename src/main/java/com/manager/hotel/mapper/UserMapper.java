package com.manager.hotel.mapper;

import com.manager.hotel.model.dto.ReadUserDTO;
import com.manager.hotel.model.entity.Authority;
import com.manager.hotel.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    ReadUserDTO readUserDTOToUser(User user);

    default String mapAuthoritiesToString(Authority authority) {
        return authority.getName();
    }
}
