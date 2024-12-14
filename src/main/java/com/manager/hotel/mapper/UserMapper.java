package com.manager.hotel.mapper;

import com.manager.hotel.model.dto.ReadUserDTO;
import com.manager.hotel.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "authorities", source = "authorities")
    ReadUserDTO readUserDTOToUser(User user);

    default Set<String> map(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toSet());
    }
}
