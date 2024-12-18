package com.manager.hotel.web;

import com.manager.hotel.model.dto.RegistrationRequest;
import com.manager.hotel.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
        @RequestBody @Valid RegistrationRequest request) throws MessagingException {
        userService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok().body(Map.of("logoutUrl", ""));
    }
}
