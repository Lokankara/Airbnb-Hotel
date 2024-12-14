package com.manager.hotel.service;

import com.manager.hotel.dao.RoleRepository;
import com.manager.hotel.dao.TokenRepository;
import com.manager.hotel.dao.UserRepository;
import com.manager.hotel.model.dto.RegistrationRequest;
import com.manager.hotel.model.entity.Role;
import com.manager.hotel.model.entity.Token;
import com.manager.hotel.model.entity.User;
import com.manager.hotel.model.enums.EmailTemplateName;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    @Value("${application.mail.frontend.activation-url}")
    private String url;

    public void register(RegistrationRequest request) throws MessagingException {
        Role role = roleRepository.findByName("USER").orElseThrow();
        User user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .accountLocked(false)
            .enabled(false)
            .roles(List.of(role))
            .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        String token = generateAndSaveActivationToken(user);
        emailService.sendEmail(user.getEmail(), user.getFullName(), EmailTemplateName.ACTIVATE_ACCOUNT, url, token, "Account activation");
    }

    private String generateAndSaveActivationToken(User user) {
        String code = generateActivationCode(6);
        Token token = Token.builder()
            .token(code)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now())
            .user(user)
            .build();
        tokenRepository.save(token);
        return code;
    }

    private String generateActivationCode(int length) {
        String chars = "0123456789";
        SecureRandom random = new SecureRandom();
        return IntStream.range(0, length)
            .mapToObj(i -> String.valueOf(chars.charAt(random.nextInt(chars.length()))))
            .collect(Collectors.joining());
    }
}
