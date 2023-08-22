package com.manager.hotel.web.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
public final class ErrorResponse {
    private final HttpStatus status;
    private final String message;
}