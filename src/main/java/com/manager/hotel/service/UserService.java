package com.manager.hotel.service;

import com.manager.hotel.config.SecurityUtils;
import com.manager.hotel.dao.UserRepository;
import com.manager.hotel.mapper.UserMapper;
import com.manager.hotel.model.dto.ReadUserDto;
import com.manager.hotel.model.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private static final String UPDATED_AT_KEY = "updated_at";
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public ReadUserDto getAuthenticatedUserFromSecurityContext() {
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = SecurityUtils.mapOauth2AttributesToUser(principal.getAttributes());
        return getByEmail(user.getEmail()).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Optional<ReadUserDto> getByEmail(String email) {
        Optional<User> oneByEmail = userRepository.findByEmail(email);
        return oneByEmail.map(userMapper::ReadUserDtoToUser);
    }

    public void syncWithIdp(OAuth2User oAuth2User, boolean forceResync) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        User user = SecurityUtils.mapOauth2AttributesToUser(attributes);
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            if (attributes.get(UPDATED_AT_KEY) != null) {
                Instant lastModifiedDate = existingUser.orElseThrow().getLastModifiedDate();
                Instant idpModifiedDate = attributes.get(UPDATED_AT_KEY) instanceof Instant instant
                    ? instant : Instant.ofEpochSecond((Integer) attributes.get(UPDATED_AT_KEY));
                if (idpModifiedDate.isAfter(lastModifiedDate) || forceResync) {
                    updateUser(user);
                }
            }
        } else {
            userRepository.saveAndFlush(user);
        }
    }

    private void updateUser(User user) {
        Optional<User> userToUpdateOpt = userRepository.findByEmail(user.getEmail());
        if (userToUpdateOpt.isPresent()) {
            User userToUpdate = userToUpdateOpt.get();
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setAuthoritiesFromGranted(user.getAuthorities());
            userToUpdate.setImageUrl(user.getImageUrl());
            userRepository.saveAndFlush(userToUpdate);
        }
    }

    public Optional<ReadUserDto> getByPublicId(UUID publicId) {
        Optional<User> oneByPublicId = userRepository.findByPublicId(publicId);
        return oneByPublicId.map(userMapper::ReadUserDtoToUser);
    }
}
