package com.manager.hotel.web.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public final class ErrorResponse {
    private final HttpStatus status;
    private final String message;
}