package com.manager.hotel.exception;

public class RoomNotFoundException
        extends RuntimeException {
    public RoomNotFoundException(
            final String message) {
        super(message);
    }
}
