package com.manager.hotel.exception;

public class GuestNotFoundException
        extends RuntimeException {
    public GuestNotFoundException(
            final String message) {
        super(message);
    }
}
